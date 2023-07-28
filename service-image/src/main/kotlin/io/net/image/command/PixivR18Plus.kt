package io.net.image.command

import com.alibaba.csp.sentinel.EntryType
import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.alibaba.csp.sentinel.slots.block.BlockException
import io.net.api.base.AbstractCmd
import io.net.api.base.Cmd
import io.net.api.base.Msg
import io.net.api.enumeration.CmdEnum
import io.net.api.exception.GroupCmdException
import io.net.image.bo.PixivRandomResult
import io.net.image.config.AppProperty
import io.net.image.entity.Image
import io.net.image.minio.MinioImageUtils
import io.net.image.repository.ImageRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import kotlin.properties.Delegates


/**
 *@author Augenstern
 *@date 2023/6/4
 */
@Cmd(cmd = CmdEnum.ST)
class PixivR18Plus(
    @Qualifier("proxy") val restTemplate: RestTemplate,
    val appProperty: AppProperty,
    val imageRepository: ImageRepository
) : AbstractCmd {

    @set:Autowired
    @setparam:Lazy
    lateinit var self: PixivR18Plus

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(PixivR18Plus::class.java)
        private const val URL = "https://image.anosu.top/pixiv/json"
    }

    override fun describe() = """
        ##P站18+##
          st([keyword])
          keyword-搜索关键字("|"分割)
    """.trimIndent()

    @SentinelResource(
        SentinelRule.ST,
        entryType = EntryType.IN,
        blockHandler = "flow",
        exceptionsToIgnore = [GroupCmdException::class]
    )
    override fun command(args: MutableList<String>): Msg {
        var bytes: ByteArray? = null
        var name: String by Delegates.notNull()
        var url: String by Delegates.notNull()
        if (args.isEmpty()) {
            val image = imageRepository.findFirstByCategoryIsOrderByCreatedDateAsc(Image.Category.PIXIV)
            if (image != null) {
                val path = image.path!!
                name = image.name!!
                url = image.url!!
                bytes = MinioImageUtils.getImage(path)
                imageRepository.delete(image)
                MinioImageUtils.removeImage(path)
            }
        }
        if (bytes == null) {
            val resolve = self.resolve(args)
            bytes = resolve.first
            val result = resolve.second
            name = result.title
            url = result.url
        }
        return Msg(
            str = name,
            bytes = bytes,
            overdue = appProperty.pixivR18RecallIn.toMillis().toInt(),
            replace = "${name}\n图片发送被限制，转为链接：\n" + url
        )
    }

    protected fun flow(args: MutableList<String>, ex: BlockException): Msg {
        throw GroupCmdException("看太多涩图对身体不好哦，休息一会吧")
    }

    @Scheduled(initialDelay = 5, fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    fun schedule() {
        logger.info("定时任务运行，开始下载图片...")
        val remainingQuantity = imageRepository.countImagesByCategoryIs(Image.Category.PIXIV)
        if (remainingQuantity >= 30) {
            logger.info("图片池已满，取消下载图片...")
            return
        }
        val (bytes, result) = self.resolve(mutableListOf())
        val uuid = UUID.randomUUID().toString()
        val image = Image(
            name = result.title,
            category = Image.Category.PIXIV,
            path = uuid,
            url = result.url
        )
        imageRepository.saveAndFlush(image)
        MinioImageUtils.putImage(uuid, bytes)
        logger.info("图片下载成功...")
    }

    private val pixivRandomResults: CopyOnWriteArrayList<PixivRandomResult> = CopyOnWriteArrayList()

    @Retryable(
        retryFor = [RuntimeException::class],
        noRetryFor = [GroupCmdException::class],
        backoff = Backoff(delay = 1000),
        recover = "fallback"
    )
    protected fun resolve(args: MutableList<String>): Pair<ByteArray, PixivRandomResult> {
        var result: PixivRandomResult
        var bytes: ByteArray? = null
        var counter = 10
        do {
            if (counter-- == 0) {
                throw GroupCmdException("图片都是坏的，请稍后再试")
            }
            result = if (args.isNotEmpty()) {
                request(args[0], 1)[0]
            } else {
                if (pixivRandomResults.isEmpty()) {
                    pixivRandomResults.addAll(request())
                }
                pixivRandomResults.removeFirst()
            }
            val entity = restTemplate.getForEntity(result.url, Resource::class.java)
            if (entity.statusCode.is2xxSuccessful) {
                bytes = entity.body!!.contentAsByteArray
            } else {
                continue
            }
        } while ((bytes == null) || !validImage(bytes))
        return bytes to result
    }

    @Suppress("UNUSED")
    protected fun fallback(e: RuntimeException): Pair<ByteArray, PixivRandomResult> {
        throw GroupCmdException("非常抱歉，由于网络异常，无法提供图片浏览服务", e)
    }

    private fun request(keyword: String = "", num: Int = 30): List<PixivRandomResult> {
        val entity = restTemplate.exchange(
            "$URL?r18=1&num=$num&size=regular&keywords=$keyword",
            HttpMethod.GET,
            HttpEntity(mutableMapOf(HttpHeaders.ACCEPT to MediaType.APPLICATION_JSON_VALUE)),
            object : ParameterizedTypeReference<List<PixivRandomResult>>() {}
        )
        if (entity.statusCode.is2xxSuccessful) {
            return entity.body!!
        } else {
            throw GroupCmdException("「${keyword}」的图片还没有呢")
        }
    }

    fun validImage(byteArray: ByteArray): Boolean {
        return try {
            byteArray.inputStream().use { ImageIO.read(it).width }
            true
        } catch (_: Exception) {
            false
        }
    }
}
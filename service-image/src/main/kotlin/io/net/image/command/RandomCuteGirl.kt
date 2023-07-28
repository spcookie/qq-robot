package io.net.image.command


import com.alibaba.csp.sentinel.EntryType
import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.alibaba.csp.sentinel.slots.block.BlockException
import com.alibaba.csp.sentinel.slots.block.RuleConstant
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager
import io.net.api.base.AbstractCmd
import io.net.api.base.Cmd
import io.net.api.base.Msg
import io.net.api.enumeration.CmdEnum
import io.net.api.exception.GroupCmdException
import io.net.image.bo.RandomUrlBO
import io.net.image.entity.Image
import io.net.image.minio.MinioImageUtils
import io.net.image.repository.ImageRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.core.io.Resource
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.nextInt

/**
 *@author Augenstern
 *@date 2023/6/4
 */
@Suppress("UNUSED")
@Cmd(cmd = CmdEnum.IMG)
class RandomCuteGirl(
    @Qualifier("direct") private val restTemplate: RestTemplate,
    private val imageRepository: ImageRepository,
) : AbstractCmd {

    @set:Autowired
    @setparam:Lazy
    lateinit var self: RandomCuteGirl

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(RandomCuteGirl::class.java)
        private const val CUTE_GIRL_URL = "http://3650000.xyz/api/?mode={mode}&type={type}"
        private const val BASE_RESOURCE = "img"
    }

    init {
        val flow = FlowRule().apply {
            resource = BASE_RESOURCE
            count = 0.05
            grade = RuleConstant.FLOW_GRADE_QPS
            controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT
        }
        FlowRuleManager.loadRules(listOf(flow))
    }

    override fun describe() = """
        ##随机小姐姐##
          img()
    """.trimIndent()

    @SentinelResource(
        BASE_RESOURCE,
        entryType = EntryType.IN,
        blockHandler = "flow",
        exceptionsToIgnore = [GroupCmdException::class]
    )
    override fun command(args: MutableList<String>): Msg {
        val img = imageRepository.findFirstByCategoryIsOrderByCreatedDateAsc(Image.Category.GIRL)
        return if (img != null) {
            val path = img.path!!
            val image = MinioImageUtils.getImage(path)
            imageRepository.delete(img)
            MinioImageUtils.removeImage(path)
            Msg(bytes = image)
        } else {
            Msg(bytes = query())
        }
    }

    protected fun flow(args: MutableList<String>, ex: BlockException): Msg {
        throw GroupCmdException("看小姐姐太频繁了，休息一会吧")
    }

    private fun query(): ByteArray {
        val of = arrayOf("1", "2", "3", "5", "8", "9")
        val mode = of[Random.nextInt(0..5)]
        return self.resolve(mode)
    }

    @Scheduled(initialDelay = 10, fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    protected fun schedule() {
        logger.info("定时任务运行，开始下载图片...")
        val remainingQuantity = imageRepository.countImagesByCategoryIs(Image.Category.GIRL)
        if (remainingQuantity >= 30) {
            logger.info("图片池已满，取消下载图片...")
            return
        }
        val of = arrayOf("1", "2", "3", "5", "8", "9")
        val mode = of[Random.nextInt(0..5)]
        try {
            val bytes = self.resolve(mode)
            val uuid = UUID.randomUUID().toString()
            val image = Image(category = Image.Category.GIRL, path = uuid)
            imageRepository.saveAndFlush(image)
            MinioImageUtils.putImage(uuid, bytes)
            logger.info("图片下载成功...")
        } catch (e: RuntimeException) {
            logger.error(e.message, e)
        }
    }

    @Retryable(exclude = [GroupCmdException::class], maxAttempts = 3, backoff = Backoff(delay = 1500))
    protected fun resolve(mode: String): ByteArray {
        val param = mapOf("mode" to mode, "type" to "json")
        val randomUrl = restTemplate.getForObject(CUTE_GIRL_URL, RandomUrlBO::class.java, param)
        val resource = restTemplate.getForEntity(randomUrl!!.url, Resource::class.java)
        return resource.body?.contentAsByteArray
            ?: throw GroupCmdException("非常抱歉，在尝试加载图片时发生了服务错误。尽管已经找到了图片，但由于某些困难，无法顺利加载。")
    }

    @Recover
    protected fun fallback(e: Exception): ByteArray {
        throw GroupCmdException("非常抱歉，目前无法成功找到图片链接。", e)
    }
}
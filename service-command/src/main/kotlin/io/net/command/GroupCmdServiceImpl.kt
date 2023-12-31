package io.net.command

import com.google.protobuf.Empty
import io.net.api.GroupCmd
import io.net.api.MsgResult
import io.net.api.MsgResultChain
import io.net.api.command.DubboGroupCmdServiceTriple
import io.net.api.enum.CmdEnum
import io.net.api.enum.CmdType
import io.net.api.enum.ServiceGroup
import io.net.api.work.WorkService
import org.apache.dubbo.common.constants.ClusterRules
import org.apache.dubbo.common.constants.LoadbalanceRules
import org.apache.dubbo.config.annotation.DubboReference
import org.apache.dubbo.config.annotation.DubboService
import org.apache.dubbo.rpc.RpcException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 *@author Augenstern
 *@since 2023/7/7
 */
@DubboService
class GroupCmdServiceImpl(
    private val cmdProperty: CmdProperty,
    @DubboReference(
        check = false,
        group = ServiceGroup.IMAGE,
        loadbalance = LoadbalanceRules.ROUND_ROBIN,
        cluster = ClusterRules.FAIL_OVER,
        timeout = 1000 * 60 * 2,
        retries = 2
    ) val imageService: WorkService,
    @DubboReference(
        check = false,
        group = ServiceGroup.TEXT,
        loadbalance = LoadbalanceRules.ROUND_ROBIN,
        cluster = ClusterRules.FAIL_OVER,
        timeout = 1000 * 60 * 2,
        retries = 2
    ) val textService: WorkService
) : DubboGroupCmdServiceTriple.GroupCmdServiceImplBase() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)
    }

    override fun invoke(request: GroupCmd): MsgResultChain {
        val ats: List<Long> = request.atsList
        if (request.botId !in ats && !request.cmd.startsWith("/")) {
            return MsgResultChain.getDefaultInstance()
        }
        val req = request.toBuilder().setCmd(request.cmd.trimStart('/')).build()
        val cmd = req.cmd
        val result: MsgResultChain
        if (cmd.isBlank()) {
            result = menu(req.groupId)
        } else {
            result = authentication(req.senderId, req.groupId, cmd) {
                try {
                    val enum = CmdEnum.valueOf(cmd.uppercase())
                    when (enum.type) {
                        CmdType.TEXT -> {
                            textService.doWork(req)
                        }

                        CmdType.CRAWLING -> {
                            MsgResultChain.getDefaultInstance()
                        }

                        CmdType.IMAGE -> {
                            imageService.doWork(req)
                        }

                        CmdType.OTHER -> {
                            when (enum) {
                                CmdEnum.HELP -> {
                                    MsgResultChain.newBuilder()
                                        .addMsgResult(MsgResult.newBuilder().setMsg(help()).build()).build()
                                }

                                else -> {
                                    throw IllegalArgumentException("不支持的命令")
                                }
                            }
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    menu(req.groupId, cmd)
                } catch (e: RpcException) {
                    error(cmd, e)
                }
            }
        }
        return result.toBuilder().putMeta("foot", foot()).build()
    }

    private inline fun authentication(
        senderId: Long,
        groupId: Long,
        cmd: String,
        crossinline block: () -> MsgResultChain
    ): MsgResultChain {
        val permission = cmdProperty.permissionWithStatus
        val sudo = cmdProperty.sudo
        val predicate = senderId in sudo
                || cmd.uppercase() == CmdEnum.HELP.name
                || permission.getOrDefault(groupId, mapOf())
            .map { it.key.name.uppercase() }
            .contains(cmd.uppercase())
        return if (predicate) {
            block()
        } else {
            menu(groupId, cmd)
        }
    }

    private fun menu(groupId: Long, cmd: String? = null): MsgResultChain {
        val manifest = buildList {
            runCatching { add(imageService.manifest(Empty.getDefaultInstance())) }.onFailure {
                logger.error(
                    "查询命令清单image时出错",
                    it
                )
            }
            runCatching { add(textService.manifest(Empty.getDefaultInstance())) }.onFailure {
                logger.error(
                    "查询命令清单text时出错",
                    it
                )
            }
        }
        val permission = cmdProperty.permissionWithStatus
        val menu = buildMap {
            manifest.map { it.menuMap }.forEach { putAll(it) }
        }
        return permission[groupId]?.run {
            val names = this.filterValues { it }.keys.map { it.name }
            val str = buildString {
                if (cmd != null) {
                    appendLine("未找到「${cmd}」命令，以下是可用命令：")
                }
                for (name in names) {
                    menu[name]?.also {
                        appendLine(it)
                    }
                }
                append(
                    """
                    ##帮助##
                      help()
                    """.trimIndent()
                )
            }
            MsgResultChain.newBuilder().addMsgResult(MsgResult.newBuilder().setMsg(str).build()).build()
        } ?: MsgResultChain.getDefaultInstance()
    }

    private fun help() = """
        ①指令：
            @我 指令名 参数1 参数2
            a.指令名不区分大小写
            b.[]中的参数为可选
            c.参数之间用空格隔开
        ②特殊符号：
            a.":" 特殊分隔符
    """.trimIndent()

    private fun foot() = """
            
            ∷ ${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now())}
            ∷ ${cmdProperty.version}
            ∷ ${cmdProperty.poweredBy}
            ∷ ${cmdProperty.name}
        """.trimIndent()

    private fun error(cmd: String, e: RpcException): MsgResultChain {
        logger.error("rpc异常", e)
        return MsgResultChain.newBuilder()
            .setCode(MsgResultChain.Code.RPC_ANOMALY)
            .addMsgResult(MsgResult.newBuilder().setMsg("「${cmd}」命令不可用，请稍后再试").build()).build()
    }
}
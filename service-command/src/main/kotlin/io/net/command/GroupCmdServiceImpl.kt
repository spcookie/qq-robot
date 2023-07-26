package io.net.command

import com.google.protobuf.Empty
import io.net.api.GroupCmd
import io.net.api.MsgCode
import io.net.api.MsgResult
import io.net.api.command.DubboGroupCmdServiceTriple
import io.net.api.enumeration.CmdEnum
import io.net.api.enumeration.ServiceGroup
import io.net.api.enumeration.WorkType
import io.net.api.work.WorkService
import org.apache.dubbo.common.constants.ClusterRules
import org.apache.dubbo.common.constants.LoadbalanceRules
import org.apache.dubbo.config.annotation.DubboReference
import org.apache.dubbo.config.annotation.DubboService
import org.apache.dubbo.rpc.RpcException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
        filter = ["ex"],
        loadbalance = LoadbalanceRules.ROUND_ROBIN,
        cluster = ClusterRules.FAIL_OVER,
        timeout = 1000 * 60,
        retries = 2
    ) val imageService: WorkService
) : DubboGroupCmdServiceTriple.GroupCmdServiceImplBase() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)
    }

    override fun invoke(request: GroupCmd): MsgResult {
        val cmd = request.cmd
        val ats: List<Long> = request.atsList
        if (request.botId !in ats) {
            return MsgResult.getDefaultInstance()
        }
        val result: MsgResult
        if (cmd.isBlank()) {
            result = menu(request.groupId)
        } else {
            result = authentication(request) {
                try {
                    val enum = CmdEnum.valueOf(cmd)
                    when (enum.type) {
                        WorkType.TEXT -> {
                            MsgResult.getDefaultInstance()
                        }

                        WorkType.CRAWLING -> {
                            MsgResult.getDefaultInstance()
                        }

                        WorkType.IMAGE -> {
                            imageService.doWork(request)
                        }

                        WorkType.OTHER -> {
                            when (enum) {
                                CmdEnum.HELP -> {
                                    MsgResult.newBuilder().setMsg(help()).build()
                                }

                                else -> {
                                    throw IllegalArgumentException("不支持的命令")
                                }
                            }
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    menu(request.groupId, cmd)
                } catch (e: RpcException) {
                    error(cmd)
                }
            }
        }
        return result.toBuilder().setFoot(foot()).build()
    }

    private inline fun authentication(
        request: GroupCmd,
        crossinline block: () -> MsgResult
    ): MsgResult {
        val permission = cmdProperty.permissionWithStatus
        val sudo = cmdProperty.sudo
        val predicate = request.senderId in sudo || permission.getOrDefault(request.groupId, mapOf())
            .map { it.key.name.uppercase() }.contains(request.cmd.uppercase())
        return if (predicate) {
            block()
        } else {
            menu(request.groupId, request.cmd)
        }
    }

    private fun menu(groupId: Long, cmd: String? = null): MsgResult {
        val manifest = buildList {
            try {
                add(imageService.manifest(Empty.getDefaultInstance()))
            } catch (e: RuntimeException) {
                logger.error("查询命令清单时出错", e)
            }
        }
        val permission = cmdProperty.permissionWithStatus
        val menu = manifest.map { it.menuMap }.reduce { acc, map ->
            acc.putAll(map)
            acc
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
            MsgResult.newBuilder().setMsg(str).build()
        } ?: MsgResult.getDefaultInstance()
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
        
            ============================
            name: ${cmdProperty.name}
            version: ${cmdProperty.version}
            powered-by: ${cmdProperty.poweredBy}
        """.trimIndent()

    private fun error(cmd: String): MsgResult {
        return MsgResult.newBuilder().setCode(MsgCode.RPC_ANOMALY).setMsg("${cmd}命令不可用，请稍后再试").build()
    }
}
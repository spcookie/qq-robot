package io.net.command

import com.google.protobuf.Empty
import io.net.api.GroupCmd
import io.net.api.MsgResult
import io.net.api.MsgResultChain
import io.net.api.command.DubboGroupCmdServiceTriple
import io.net.api.enumeration.CmdEnum
import io.net.api.enumeration.CmdType
import io.net.api.enumeration.ServiceGroup
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
        loadbalance = LoadbalanceRules.ROUND_ROBIN,
        cluster = ClusterRules.FAIL_OVER,
        timeout = 1000 * 60 * 2,
        retries = 2
    ) val imageService: WorkService
) : DubboGroupCmdServiceTriple.GroupCmdServiceImplBase() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)
    }

    override fun invoke(request: GroupCmd): MsgResultChain {
        val cmd = request.cmd
        val ats: List<Long> = request.atsList
        if (request.botId !in ats) {
            return MsgResultChain.getDefaultInstance()
        }
        val result: MsgResultChain
        if (cmd.isBlank()) {
            result = menu(request.groupId)
        } else {
            result = authentication(request) {
                try {
                    val enum = CmdEnum.valueOf(cmd)
                    when (enum.type) {
                        CmdType.TEXT -> {
                            MsgResultChain.getDefaultInstance()
                        }

                        CmdType.CRAWLING -> {
                            MsgResultChain.getDefaultInstance()
                        }

                        CmdType.IMAGE -> {
                            imageService.doWork(request)
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
                    menu(request.groupId, cmd)
                } catch (e: RpcException) {
                    error(cmd)
                }
            }
        }
        return result.toBuilder().putMeta("foot", foot()).build()
    }

    private inline fun authentication(
        request: GroupCmd,
        crossinline block: () -> MsgResultChain
    ): MsgResultChain {
        val permission = cmdProperty.permissionWithStatus
        val sudo = cmdProperty.sudo
        val predicate = request.senderId in sudo
                || request.cmd.uppercase() == CmdEnum.HELP.name
                || permission.getOrDefault(request.groupId, mapOf())
            .map { it.key.name.uppercase() }
            .contains(request.cmd.uppercase())
        return if (predicate) {
            block()
        } else {
            menu(request.groupId, request.cmd)
        }
    }

    private fun menu(groupId: Long, cmd: String? = null): MsgResultChain {
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
            
               ฅฅฅฅฅฅฅฅ
            ∷ @${cmdProperty.name}
            ∷ v${cmdProperty.version}
            ∷ ${cmdProperty.poweredBy}
        """.trimIndent()

    private fun error(cmd: String): MsgResultChain {
        return MsgResultChain.newBuilder()
            .setCode(MsgResultChain.Code.RPC_ANOMALY)
            .addMsgResult(MsgResult.newBuilder().setMsg("「${cmd}」命令不可用，请稍后再试").build()).build()
    }
}
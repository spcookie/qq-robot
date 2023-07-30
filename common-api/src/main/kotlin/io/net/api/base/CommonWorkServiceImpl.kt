package io.net.api.base

import com.google.protobuf.Empty
import io.net.api.GroupCmd
import io.net.api.MsgResultChain
import io.net.api.work.DubboWorkServiceTriple
import io.net.api.work.Menu
import jakarta.annotation.PostConstruct
import org.springframework.core.annotation.AnnotationUtils

/**
 *@author Augenstern
 *@since 2023/7/29
 */
class CommonWorkServiceImpl(private val cmds: List<AbstractCmd>) : DubboWorkServiceTriple.WorkServiceImplBase() {

    private val matchedCmds: MutableMap<String, AbstractCmd> = mutableMapOf()

    @PostConstruct
    private fun matching() {
        for (cmd in cmds) {
            val anno = AnnotationUtils.findAnnotation(cmd::class.java, Cmd::class.java)
            if (anno != null) {
                matchedCmds[anno.cmd.name.uppercase()] = cmd
            }
        }
    }

    override fun doWork(request: GroupCmd): MsgResultChain {
        return matchedCmds[request.cmd]?.run {
            ContextHolder.use(ContextHolder.Context(request.botId, request.groupId, request.senderId)) {
                command(args().strategy(request.argsList)).protobuf()
            }
        } ?: MsgResultChain.getDefaultInstance()
    }

    override fun manifest(request: Empty?): Menu {
        val map = buildMap {
            matchedCmds.forEach { (name, cmd) ->
                put(name, cmd.describe())
            }
        }
        return Menu.newBuilder().putAllMenu(map).build()
    }
}
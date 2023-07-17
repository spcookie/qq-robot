package io.net.image.service

import com.google.protobuf.Empty
import io.net.api.GroupCmd
import io.net.api.MsgResult
import io.net.api.base.AbstractCmd
import io.net.api.base.Cmd
import io.net.api.enumeration.ServiceGroup
import io.net.api.work.DubboWorkServiceTriple
import io.net.api.work.Menu
import jakarta.annotation.PostConstruct
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.core.annotation.AnnotationUtils

/**
 *@author Augenstern
 *@since 2023/7/8
 */
@DubboService(group = ServiceGroup.IMAGE)
class ImageServiceImpl(private val cmds: List<AbstractCmd>) : DubboWorkServiceTriple.WorkServiceImplBase() {

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

    override fun doWork(request: GroupCmd): MsgResult {
        val result = matchedCmds[request.cmd]?.run {
            command(args().strategy(request.argsList)).proto()
        } ?: MsgResult.getDefaultInstance()
        return result
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
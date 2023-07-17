package io.net.command

import io.net.api.enumeration.CmdEnum
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

/**
 *@author Augenstern
 *@since 2023/7/8
 */
@Validated
@ConfigurationProperties(prefix = "cmd")
class CmdProperty {
    val permissionWithStatus: MutableMap<Long, MutableMap<CmdEnum, Boolean>> by lazy {
        val lazy: MutableMap<Long, MutableMap<CmdEnum, Boolean>> = mutableMapOf()
        permission.forEach { (id, cmdEnums) ->
            var inited = false
            val cmdEnumsMap = lazy.computeIfAbsent(id) {
                inited = true
                cmdEnums.associateWith { true }.toMutableMap()
            }
            if (!inited) {
                cmdEnumsMap.putAll(cmdEnums.associateWith { true })
            }
        }
        lazy
    }
    lateinit var permission: MutableMap<Long, MutableList<CmdEnum>>
    lateinit var sudo: List<Long>
    var version: String = ""
    var name: String = ""
    var poweredBy: String = ""
}
package io.net.api.base

import io.net.api.enumeration.CmdEnum
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@date 2023/6/1
 */
@Component
annotation class Cmd(
    val cmd: CmdEnum,
)
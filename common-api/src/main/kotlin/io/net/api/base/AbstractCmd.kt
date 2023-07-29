package io.net.api.base

import io.net.api.MsgResultChain

/**
 *@author Augenstern
 *@date 2023/6/1
 */
interface AbstractCmd {
    fun describe(): String
    fun args(): ArgsMergeStrategy = ArgsMergeStrategy.Default
    fun command(args: MutableList<String>): MsgResultChain
}
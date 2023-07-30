package io.net.api.enum

/**
 *@author Augenstern
 *@date 2023/6/1
 */
enum class CmdEnum(val type: CmdType) {
    GPT(CmdType.TEXT),
    IMG(CmdType.IMAGE),
    ST(CmdType.IMAGE),
    STEAM(CmdType.TEXT),
    HELP(CmdType.OTHER)
}
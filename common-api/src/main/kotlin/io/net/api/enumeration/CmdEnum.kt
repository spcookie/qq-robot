package io.net.api.enumeration

/**
 *@author Augenstern
 *@date 2023/6/1
 */
enum class CmdEnum(val type: WorkType) {
    GPT(WorkType.TEXT),
    IMG(WorkType.IMAGE),
    ST(WorkType.IMAGE),
    STEAM(WorkType.TEXT),
    HELP(WorkType.OTHER)
}
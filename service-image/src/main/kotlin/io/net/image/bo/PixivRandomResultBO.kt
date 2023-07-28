package io.net.image.bo

import java.io.Serializable

/**
 *@author Augenstern
 *@date 2023/6/4
 */
data class PixivRandomResultBO(
    val pid: Long = 0,
    val page: Int = 0,
    val uid: Long = 0,
    val title: String = "",
    val user: String = "",
    val r18: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val tags: List<String> = listOf(),
    val url: String = ""
) : Serializable

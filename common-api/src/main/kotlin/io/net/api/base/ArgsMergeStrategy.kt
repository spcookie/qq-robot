package io.net.api.base

/**
 *@author Augenstern
 *@date 2023/6/8
 */
interface ArgsMergeStrategy {
    fun strategy(originalArgs: List<String>): MutableList<String>

    companion object {
        val Default = object : ArgsMergeStrategy {
            override fun strategy(originalArgs: List<String>): MutableList<String> {
                return originalArgs.toMutableList()
            }
        }
        val Merge = object : ArgsMergeStrategy {
            override fun strategy(originalArgs: List<String>): MutableList<String> {
                return mutableListOf(originalArgs.reduce { acc, s ->
                    acc + s
                })
            }
        }
    }
}
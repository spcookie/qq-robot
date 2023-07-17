package io.net.api.base

import com.google.protobuf.ByteString
import io.net.api.MsgResult

/**
 *@author Augenstern
 *@since 2023/7/13
 */
data class Msg(
    val str: String? = null,
    val bytes: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Msg) return false

        if (str != other.str) return false
        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        var result = str.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }

    fun proto(): MsgResult = MsgResult.newBuilder().apply {
        if (str != null) {
            msg = str
        }
        if (this@Msg.bytes != null) {
            bytes = ByteString.copyFrom(this@Msg.bytes)
        }
    }.build()
}

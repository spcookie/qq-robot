package io.net.api.base

import com.google.protobuf.ByteString
import io.net.api.MsgResult
import io.net.api.MsgResult.Data
import io.net.api.MsgResult.Receipt

/**
 *@author Augenstern
 *@since 2023/7/13
 */
data class Msg(
    val str: String? = null,
    val bytes: ByteArray? = null,
    val overdue: Int? = null,
    val replace: String? = null
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
        if (bytes != null) {
            data = Data.newBuilder()
                .setType(Data.MediaType.PICTURE)
                .setBytes(ByteString.copyFrom(bytes))
                .build()
        }
        receipt = Receipt.newBuilder().apply {
            if (overdue != null) {
                recall = overdue
            }
            if (replace != null) {
                fallback = replace
            }
        }.build()
    }.build()
}

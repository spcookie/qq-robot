package io.net.api.base

import com.google.protobuf.ByteString
import io.net.api.MsgResult
import io.net.api.MsgResult.Data
import io.net.api.MsgResultChain

/**
 *@author Augenstern
 *@since 2023/7/30
 */
data class MsgChain(
    val msgs: List<Msg>,
    val receipt: Receipt = Receipt(0, ""),
    val meta: Map<String, String> = mapOf()
) {
    fun protobuf(): MsgResultChain = MsgResultChain.newBuilder()
        .addAllMsgResult(msgs.map {
            val type = when (it.data.type) {
                Msg.Data.Type.PICTURE -> MsgResult.Data.MediaType.PICTURE
                Msg.Data.Type.AUDIO -> MsgResult.Data.MediaType.AUDIO
            }
            MsgResult.newBuilder()
                .setData(
                    Data.newBuilder()
                        .setType(type)
                        .setBytes(ByteString.copyFrom(it.data.bytes))
                        .build()
                )
                .setMsg(it.msg)
                .build()
        })
        .setReceipt(
            MsgResultChain.Receipt.newBuilder().setRecall(receipt.recall).setFallback(receipt.fallback).build()
        )
        .putAllMeta(meta)
        .build()
}

data class Msg(
    val msg: String = "",
    val data: Data = Data(Data.Type.PICTURE, ByteArray(0))
) {
    data class Data(
        val type: Type,
        val bytes: ByteArray
    ) {
        enum class Type { PICTURE, AUDIO }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Data) return false

            if (type != other.type) return false
            return bytes.contentEquals(other.bytes)
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + bytes.contentHashCode()
            return result
        }
    }
}

data class Receipt(
    val recall: Int,
    val fallback: String
)
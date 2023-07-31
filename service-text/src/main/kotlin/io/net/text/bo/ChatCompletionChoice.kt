package io.net.text.bo

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class ChatCompletionChoice : Serializable {
    /**
     * This index of this completion in the returned list.
     */
    var index: Int? = null

    /**
     * The [ChatMessageRole.assistant] message or delta (when streaming) which was generated
     */
    @JsonAlias("delta")
    var message: ChatMessage? = null

    /**
     * The reason why GPT stopped generating, for example "length".
     */
    @JsonProperty("finish_reason")
    var finishReason: String? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatCompletionChoice) return false

        if (index != other.index) return false
        if (message != other.message) return false
        return finishReason == other.finishReason
    }

    override fun hashCode(): Int {
        var result = index ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (finishReason?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ChatCompletionChoice(" +
                "index=$index, " +
                "message=$message, " +
                "finishReason=$finishReason" +
                ")"
    }
}


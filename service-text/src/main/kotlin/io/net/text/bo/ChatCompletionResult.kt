package io.net.text.bo

import java.io.Serializable


class ChatCompletionResult : Serializable {
    /**
     * Unique id assigned to this chat completion.
     */
    var id: String? = null

    /**
     * The type of object returned, should be "chat.completion"
     */
    var `object`: String? = null

    /**
     * The creation time in epoch seconds.
     */
    var created: Long = 0

    /**
     * The GPT model used.
     */
    var model: String? = null

    /**
     * A list of all generated completions.
     */
    var choices: List<ChatCompletionChoice>? = null

    /**
     * The API usage for this request.
     */
    var usage: Usage? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatCompletionResult) return false

        if (id != other.id) return false
        if (`object` != other.`object`) return false
        if (created != other.created) return false
        if (model != other.model) return false
        if (choices != other.choices) return false
        return usage == other.usage
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (`object`?.hashCode() ?: 0)
        result = 31 * result + created.hashCode()
        result = 31 * result + (model?.hashCode() ?: 0)
        result = 31 * result + (choices?.hashCode() ?: 0)
        result = 31 * result + (usage?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ChatCompletionResult(" +
                "id=$id, " +
                "`object`=$`object`, " +
                "created=$created, " +
                "model=$model, " +
                "choices=$choices, " +
                "usage=$usage" +
                ")"
    }

}
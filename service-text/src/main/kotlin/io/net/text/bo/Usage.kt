package io.net.text.bo

import com.fasterxml.jackson.annotation.JsonProperty


class Usage {
    /**
     * The number of prompt tokens used.
     */
    @JsonProperty("prompt_tokens")
    var promptTokens: Long = 0

    /**
     * The number of completion tokens used.
     */
    @JsonProperty("completion_tokens")
    var completionTokens: Long = 0

    /**
     * The number of total tokens used
     */
    @JsonProperty("total_tokens")
    var totalTokens: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Usage) return false

        if (promptTokens != other.promptTokens) return false
        if (completionTokens != other.completionTokens) return false
        return totalTokens == other.totalTokens
    }

    override fun hashCode(): Int {
        var result = promptTokens.hashCode()
        result = 31 * result + completionTokens.hashCode()
        result = 31 * result + totalTokens.hashCode()
        return result
    }

    override fun toString(): String {
        return "Usage(" +
                "promptTokens=$promptTokens, " +
                "completionTokens=$completionTokens, " +
                "totalTokens=$totalTokens" +
                ")"
    }
}
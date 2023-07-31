package io.net.text.bo

import com.fasterxml.jackson.databind.JsonNode
import java.io.Serializable

class ChatFunctionCall : Serializable {
    /**
     * The name of the function being called
     */
    var name: String? = null

    /**
     * The arguments of the call produced by the model, represented as a JsonNode for easy manipulation.
     */
    var arguments: JsonNode? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatFunctionCall) return false

        if (name != other.name) return false
        return arguments == other.arguments
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (arguments?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ChatFunctionCall(name=$name, arguments=$arguments)"
    }
}
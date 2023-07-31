package io.net.text.bo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.function.Function


class ChatFunction : Serializable {
    private val name: String? = null

    private val description: String? = null

    @JsonProperty("parameters")
    private val parametersClass: Class<*>? = null

    @JsonIgnore
    private val executor: Function<Any, Any>? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatFunction) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (parametersClass != other.parametersClass) return false
        return executor == other.executor
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (parametersClass?.hashCode() ?: 0)
        result = 31 * result + (executor?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ChatFunction(" +
                "name=$name, " +
                "description=$description, " +
                "parametersClass=$parametersClass, " +
                "executor=$executor" +
                ")"
    }
}
package io.net.text

import io.net.text.command.Chat
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TextApplicationTests {

    @Autowired
    lateinit var chat: Chat

    @ParameterizedTest
    @DisplayName("chat")
    @ValueSource(strings = ["你好!"])
    fun chat(msg: String) {
        val chain = chat.command(mutableListOf(msg))
        Assertions.assertNotEquals("", chain.msgs[0].msg, "chat回复不为空")
    }

    @Test
    @DisplayName("没有参数chat")
    fun chat_not_param() {
        Assertions.assertThrows(ConstraintViolationException::class.java, {
            chat.command(mutableListOf())
        }, "应该抛出ConstraintViolationException")
    }

}

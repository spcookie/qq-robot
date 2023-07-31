package io.net.image

import com.alibaba.csp.sentinel.slots.block.BlockException
import io.net.image.command.PixivR18Plus
import io.net.image.command.RandomCuteGirl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@SpringBootTest
class ImageApplicationTests {

    @set:Autowired
    lateinit var randomCuteGirl: RandomCuteGirl

    @set:Autowired
    lateinit var pixivR18Plus: PixivR18Plus

    @Test
    @DisplayName("img限流")
    fun img() {
        val latch = CountDownLatch(4)
        repeat(4) {
            Thread {
                try {
                    Assertions.assertThrows(BlockException::class.java, {
                        randomCuteGirl.command(mutableListOf(""))
                    }, "应该抛出BlockException")
                } finally {
                    latch.countDown()
                }
            }.start()
        }
        latch.await()
        TimeUnit.SECONDS.sleep(5)
    }

    @Test
    @DisplayName("st")
    fun st() {
        val chain = pixivR18Plus.command(mutableListOf())
        Assertions.assertTrue(chain.msgs[0].data.bytes.isNotEmpty(), "图片不能为空")
    }

}

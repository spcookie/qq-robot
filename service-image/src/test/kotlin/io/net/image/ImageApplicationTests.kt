package io.net.image

import io.net.image.command.PixivR18Plus
import io.net.image.command.RandomCuteGirl
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
    fun img() {
        val latch = CountDownLatch(4)
        repeat(4) {
            Thread {
                try {
                    val msg = randomCuteGirl.command(mutableListOf(""))
                    println(msg)
                } finally {
                    latch.countDown()
                }
            }.start()
        }
        latch.await()
        TimeUnit.SECONDS.sleep(5)
    }

    @Test
    fun st() {
        val msg = pixivR18Plus.command(mutableListOf())
        println(msg.str)
    }

}

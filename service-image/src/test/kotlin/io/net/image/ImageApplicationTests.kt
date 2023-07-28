package io.net.image

import io.net.image.command.RandomCuteGirl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch

@SpringBootTest
class ImageApplicationTests {

    @set:Autowired
    lateinit var randomCuteGirl: RandomCuteGirl

    @Test
    fun img_() {
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
    }

}

package coroutines

import java.security.SecureRandom

class DummyService(private val name: String) {

    private val random = SecureRandom()

    fun content(): ContentDuration {
        val duration = random.nextInt(5000)
        Thread.sleep(duration.toLong())
        return ContentDuration(name, duration)
    }
}

data class ContentDuration(val content: String, val duration: Int)
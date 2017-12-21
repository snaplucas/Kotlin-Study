package coroutines

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.Duration
import java.time.Instant

class CoroutineTest {

    private val DummyService.asyncContent: Deferred<ContentDuration>
        get() = async(CommonPool) { content() }

    @Test
    fun shouldBeParallel() {
        val services = listOf("Service A", "Service B", "Service C", "Service X", "Service Y", "Service Z")
        val start = Instant.now()
        val results = runBlocking {
            services.map { DummyService(it) }
                    .map { it.asyncContent }
                    .map { it.await() }
        }
        val end = Instant.now()
        results.forEach { println(it) }
        assertThat(results).isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(services)
        val maxTimeElapsed = results.maxBy { it -> it.duration }?.duration?.toLong()
        println("Time taken by the longest service is  $maxTimeElapsed milliseconds")
        val duration = Duration.between(start, end)
        val timeElapsed = duration.toMillis()
        println("Time taken by the co-routine block is $timeElapsed milliseconds")
        assertThat(timeElapsed).isGreaterThanOrEqualTo(maxTimeElapsed)
    }
}
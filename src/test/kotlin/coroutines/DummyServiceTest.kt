package coroutines

import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.Duration
import java.time.Instant

@DelicateCoroutinesApi
class CoroutineTest {

    private val DummyService.asyncContent: Deferred<ContentDuration>
        get() = GlobalScope.async { content() }

    private fun contentAsync(dummy: DummyService): Deferred<ContentDuration> {
        return GlobalScope.async { dummy.content() }
    }

    @Test
    fun shouldBeParallel() {
        val services = listOf("Service A", "Service B", "Service C", "Service X", "Service Y", "Service Z")
        val start = Instant.now()
        val results = runBlocking {
            services.map { DummyService(it) }
                    .map { contentAsync(it) }
                    .map { it.await() }
        }
        val end = Instant.now()
        results.forEach { println(it) }
        assertThat(results).isNotNull
                .isNotEmpty
                .hasSameSizeAs(services)
        val maxTimeElapsed = results.maxByOrNull { it.duration }?.duration?.toLong()
        println("Time taken by the longest service is  $maxTimeElapsed milliseconds")
        val duration = Duration.between(start, end)
        val timeElapsed = duration.toMillis()
        println("Time taken by the co-routine block is $timeElapsed milliseconds")
        assertThat(timeElapsed).isGreaterThanOrEqualTo(maxTimeElapsed)
    }
}
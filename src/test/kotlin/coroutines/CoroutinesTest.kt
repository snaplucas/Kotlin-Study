package coroutines

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import shared.ContentDuration
import shared.DummyService
import java.time.Duration
import java.time.Instant

class CoroutineTest {

    private lateinit var services: Array<String>
    private lateinit var lambdas: List<DummyService>

    @BeforeMethod internal fun setUp() {
        services = arrayOf("Service A", "Service B", "Service C", "Service X", "Service Y", "Service Z")
        lambdas = services.map { DummyService(it) }
    }

    private val DummyService.asyncContent: Deferred<ContentDuration>
        get() = kotlinx.coroutines.experimental.async(kotlinx.coroutines.experimental.CommonPool) { content }

    @Test
    fun shouldBeParallel() {
        val start = Instant.now()
        val results = runBlocking {
            lambdas.map { it.asyncContent }
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
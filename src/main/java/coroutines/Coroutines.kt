@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package coroutines

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking


fun main2(args: Array<String>) = runBlocking {
    // start fundamentals.coroutines.main coroutine
    launch(CommonPool) {
        // create new coroutine in common thread pool
        delay(1000L)
        println("World!")
    }
    println("Hello,") // fundamentals.coroutines.main coroutine continues while child is delayed
    delay(2000L) // non-blocking delay for 2 seconds to keep JVM alive
}

fun main3(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) { doWorld() }
    println("Hello,")
    job.join()
}

private suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

fun main4(args: Array<String>) = runBlocking {
    val jobs = List(100_000) { // create a lot of coroutines and list their jobs
        launch(CommonPool) {
            delay(1000L)
            print("")
        }
    }
    jobs.forEach { it.join() } // wait for all jobs to complete
}

fun main(args: Array<String>) = runBlocking {
    launch(CommonPool) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
}
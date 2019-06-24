package coroutines

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.system.measureTimeMillis

class CouroutinesTest {

    @Test
    fun main1() = runBlocking {
        // start fundamentals.coroutines.main coroutine
        GlobalScope.launch {
            // create new coroutine in common thread pool
            delay(1000L)
            println("World!")
        }
        println("Hello,") // fundamentals.coroutines.main coroutine continues while child is delayed
        delay(2000L) // non-blocking delay for 2 seconds to keep JVM alive
    }

    @Test

    fun main3() = runBlocking {
        val job = GlobalScope.launch { doWorld() }
        println("Hello,")
        job.join()
    }

    private suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }

    @Test
    fun main4() = runBlocking {
        val jobs = List(100_000) {
            // create a lot of coroutines and list their jobs
            GlobalScope.launch {
                delay(1000L)
                print(".")
            }
        }
        jobs.forEach { it.join() } // wait for all jobs to complete
    }

    @Test
    fun main5() = runBlocking {
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // just quit after delay
    }

    @Test
    fun main6() = runBlocking {
        val job = GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to ensure it was cancelled indeed
        println("main: Now I can quit.")
    }

    @Test
    fun main7() = runBlocking {
        val job = GlobalScope.launch {
            var nextPrintTime = System.currentTimeMillis()
            var i = 0
            while (i < 10) { // computation loop
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to see if it was cancelled....
        println("main: Now I can quit.")
    }

    @Test
    fun main8() = runBlocking {
        val job = GlobalScope.launch {
            var nextPrintTime = 0L
            var i = 0
            while (isActive) { // cancellable computation loop
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime = currentTime + 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to see if it was cancelled....
        println("main: Now I can quit.")
    }

    @Test
    fun main9() = runBlocking {
        val job = GlobalScope.launch {
            try {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("I'm running finally")
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to ensure it was cancelled indeed
        println("main: Now I can quit.")
    }

    @Test(expected = TimeoutException::class)
    fun man10() = runBlocking {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    @Test
    fun main11() = runBlocking {
        val time = measureTimeMillis {
            val one = somethingUsefulOneAsync()
            val two = GlobalScope.async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    private fun somethingUsefulOneAsync() = GlobalScope.async { doSomethingUsefulOne() }

    private suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }
}
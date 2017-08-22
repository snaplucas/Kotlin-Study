@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package coroutines

import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis


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
    val jobs = List(100_000) {
        // create a lot of coroutines and list their jobs
        launch(CommonPool) {
            delay(1000L)
            print("")
        }
    }
    jobs.forEach { it.join() } // wait for all jobs to complete
}

fun main5(args: Array<String>) = runBlocking {
    launch(CommonPool) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
}

fun main6(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
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

fun main7(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
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

fun main8(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
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

fun main9(args: Array<String>) = runBlocking {
    val job = launch(CommonPool) {
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

fun main10(args: Array<String>) = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}

fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        val one = asyncSomethingUsefulOne()
        val two = async(CommonPool) { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

private fun asyncSomethingUsefulOne() = async(CommonPool) { doSomethingUsefulOne() }

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}
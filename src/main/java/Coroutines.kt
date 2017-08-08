@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

@Suppress("EXPERIMENTAL_FEATURE_WARNING")

class Coroutines {

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun <T> async(block: suspend () -> T) {}
}

fun main(args: Array<String>) {
    println("Start")

    // Start a coroutine
    launch(CommonPool) {
        delay(1000)
        println("Hello")
    }

    Thread.sleep(2000) // wait for 2 seconds
    println("Stop")
}
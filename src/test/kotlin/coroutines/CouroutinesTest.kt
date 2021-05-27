package coroutines

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.system.measureTimeMillis

@DelicateCoroutinesApi
class CouroutinesTest {

    @Test
    fun testing1() = runBlocking {
        // start fundamentals.coroutines.testing coroutine
        GlobalScope.launch {
            // create new coroutine in common thread pool
            delay(1000L)
            println("World!")
        }
        println("Hello,") // fundamentals.coroutines.testing coroutine continues while child is delayed
        delay(2000L) // non-blocking delay for 2 seconds to keep JVM alive
    }

    @Test
    fun testing3() = runBlocking {
        val job = GlobalScope.launch { doWorld() }
        println("Hello,")
        job.join()
    }

    private suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }

    @Test
    fun testing4() = runBlocking {
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
    fun testing5() = runBlocking {
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // just quit after delay
    }

    @Test
    fun testing6() = runBlocking {
        val job = GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // delay a bit
        println("testing: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to ensure it was cancelled indeed
        println("testing: Now I can quit.")
    }

    @Test
    fun testing7() = runBlocking {
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
        println("testing: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to see if it was cancelled....
        println("testing: Now I can quit.")
    }

    @Test
    fun testing8() = runBlocking {
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
        println("testing: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to see if it was cancelled....
        println("testing: Now I can quit.")
    }

    @Test
    fun testing9() = runBlocking {
        val job = launch {
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
        println("testing: I'm tired of waiting!")
        job.cancel() // cancels the job
        delay(1300L) // delay a bit to ensure it was cancelled indeed
        println("testing: Now I can quit.")
    }

    @Test(expected = TimeoutException::class)
    fun testing10() = runBlocking {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    private fun somethingUsefulOneAsync(): Deferred<Int> = GlobalScope.async { doSomethingUsefulOne() }

    private suspend fun doSomethingUsefulOne(): Int {
        delay(1000L)
        println("hey")
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        println("hey")
        return 29
    }

    @Test
    fun testing12() {
        runBlocking {
            listOf(someFunctionAsync(), someFunctionAsync(), someFunctionAsync()).awaitAll()
        }
    }

    private fun someFunctionAsync() = GlobalScope.async {
        println("hey")
        delay(5000L)
        println("hola")
    }

    @Test
    fun testing13() {
        runBlocking {
            val one = async { someSuspendFunction() }
            val two = async { someSuspendFunction() }
            val three = async { someSuspendFunction() }

            val list = listOf(one, two, three)
            list.awaitAll()
        }
    }

    private suspend fun someSuspendFunction() {
        println("hey")
        delay(2000L)
        println("hola")
    }

    @Test
    fun testing14() {
        runBlocking {
            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
                // some computation
                one.start() // start the first one
                two.start() // start the second one
                println("The answer is ${one.await() + two.await()}")
            }
            println("Completed in $time ms")
        }
    }

    @Test
    fun testing11() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    @Test
    fun testing15() = runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    private suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }
}
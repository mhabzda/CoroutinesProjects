import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val counter = AtomicInteger(0)
        withContext(Dispatchers.Default) {
            massiveRunAtomic { counter.incrementAndGet() }
        }
        println("Counter = ${counter.get()}")
    }
}

suspend fun massiveRunAtomic(action: suspend () -> Unit) {
    val coroutinesNumber = 100
    val actionRepeatsNumber = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(coroutinesNumber) {
                launch {
                    repeat(actionRepeatsNumber) {
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${coroutinesNumber * actionRepeatsNumber} actions in $time ms")
}
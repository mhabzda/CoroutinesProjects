import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val mutex = Mutex()
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRunMutex {
                mutex.withLock { counter++ }
            }
        }
        println("Counter = $counter")
    }
}

suspend fun massiveRunMutex(action: suspend () -> Unit) {
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
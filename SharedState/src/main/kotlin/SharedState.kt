import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRun { counter++ }
        }
        println("Counter = $counter")
    }
}

suspend fun massiveRun(action: suspend () -> Unit) {
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
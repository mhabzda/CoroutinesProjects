import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        runCoarseGrainedProcessing()
    }

    runBlocking {
        runFineGrainedProcessing()
    }
}

private suspend fun runFineGrainedProcessing() {
    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0
    withContext(Dispatchers.Default) {
        massiveRunThreads ({
            withContext(counterContext) {
                counter++
            }
        }, "fine-grained")
    }
    println("Counter from fine-grained = $counter")
}

private suspend fun runCoarseGrainedProcessing() {
    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0
    withContext(counterContext) {
        massiveRunThreads ({
            counter++
        }, "coarse-grained")
    }
    println("Counter from coarse-grained = $counter")
}

suspend fun massiveRunThreads(action: suspend () -> Unit, type: String) {
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
    println("Completed ${coroutinesNumber * actionRepeatsNumber} actions in $time ms for $type processing")
}
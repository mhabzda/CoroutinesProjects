import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    runBlocking {
        val flow = sendNewNumbers()
        println("Flow hasn't started yet")
        withTimeoutOrNull(1000L) {
            flow.collect { println(it) }
        }
    }
}

fun sendNewNumbers() = flow {
    listOf(1, 2, 3).forEach {
        delay(400L)
        emit(it)
    }
}
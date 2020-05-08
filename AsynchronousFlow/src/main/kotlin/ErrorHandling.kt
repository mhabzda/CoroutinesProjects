import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
//        tryCatch()
//        catch()
        onCompletion()
    }
}

suspend fun onCompletion() {
    (1..3).asFlow()
        .onEach { check(it != 2) }
        .onCompletion { cause ->
            if (cause != null) {
                println("Flow completed with exception: $cause")
            } else {
                println("Flow completed successfully")
            }
        }
        .catch { exception -> println("Caught exception: $exception") }
        .collect {
            println(it)
        }
}

suspend fun catch() {
    (1..3).asFlow()
        .onEach { check(it != 2) }
        .catch { exception -> println("Caught exception: $exception") }
        .collect {
            println(it)
        }
}

suspend fun tryCatch() {
    try {
        (1..3).asFlow()
            .onEach { check(it != 2) }
            .collect {
                println(it)
            }
    } catch (exception: Exception) {
        println("Caught exception: $exception")
    }
}
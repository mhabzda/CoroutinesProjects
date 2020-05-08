import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }
        val job = GlobalScope.launch(exceptionHandler) {
            println("Throwing exception from job")
            throw IndexOutOfBoundsException("exception from launch")
        }
        job.join()

        val deferred: Deferred<Int> = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException("exception from async")
        }

        try {
            deferred.await()
        } catch (exception: ArithmeticException) {
            println("Caught exception ${exception.localizedMessage}")
        }
    }
}
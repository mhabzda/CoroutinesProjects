import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                println("Sending info to the channel: $x on thread: ${Thread.currentThread()}")
                channel.send(x * x)
            }
            println("Closing channel on thread: ${Thread.currentThread()}")
            channel.close()
        }

        for (i in channel) {
            println("Receiving $i on thread: ${Thread.currentThread()}")
        }
    }
}
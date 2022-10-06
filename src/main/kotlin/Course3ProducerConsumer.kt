import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

suspend fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for ( x in 1..5) {
        println("Sending ${x*x}")
        send(x*x)
    }
}

fun course3ProducerConsumer() = runBlocking<Unit> {

    val channel = produceSquares()

    launch {
        channel.consumeEach {
            println("Received: $it")
        }
    }
}
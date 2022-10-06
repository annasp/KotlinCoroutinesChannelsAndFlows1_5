import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun produceNumbersFanIn(coroutineScope: CoroutineScope, channel: Channel<Int>, delay: Long) = coroutineScope.launch {
    var x:Int = delay.toInt()
    while (true) {
        channel.send(x++)
        delay(delay)
    }
}

suspend fun consumeFanIn(coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach {
        println("Received value: $it in thread ${java.lang.Thread.currentThread().name}")
    }
}

// Fan In. Many producers sending data into a single consumer
fun course3FanIn() = runBlocking {
    val channel = Channel<Int>()

    produceNumbersFanIn(this, channel, 500)
    produceNumbersFanIn(this, channel, 200)
    consumeFanIn(this, channel)

    delay(500)
    channel.cancel()
}
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun produceNumbersFanOut(coroutineScope: CoroutineScope): ReceiveChannel<Int> = coroutineScope.produce {
    var x = 1
    while (true) {
        send(x++)
        delay(500)
    }
}

suspend fun consumeFanOut(id: Int, coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach {
        println("Receivedin consumer $id value: $it in thread ${java.lang.Thread.currentThread().name}")
    }
}

// In fan out, we have one producer, and that's producing data and
// we have multiple consumers consuming that data
fun course3FanOut() = runBlocking {
    val producer = produceNumbers(this)

    repeat(5) {
        consumeFanOut(it, this, producer)
    }

    delay(5000)

    producer.cancel()
}
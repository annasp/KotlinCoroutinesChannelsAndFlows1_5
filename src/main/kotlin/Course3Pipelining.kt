import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun produceNumbers(coroutineScope: CoroutineScope): ReceiveChannel<Int> = coroutineScope.produce {
    var x = 1
    while (true) {
        send(x++)
        delay(500)
    }
}

// -1-
suspend fun createSquares(coroutineScope: CoroutineScope, numbersProducer: ReceiveChannel<Int>): ReceiveChannel<Int> = coroutineScope.produce {
    for(value in numbersProducer) send(value * value)
}

// -2-
suspend fun <T, R>map(coroutineScope: CoroutineScope, producer: ReceiveChannel<T>, transform: (T) -> R): ReceiveChannel<R> = coroutineScope.produce {
    for(value in producer) send(transform(value))
}

// -3-
suspend fun <T, R> ReceiveChannel<T>.map2(coroutineScope: CoroutineScope, transform: (T) -> R): ReceiveChannel<R> {
    val receiveChannel = this
    return coroutineScope.produce {
        try {
            for (value in receiveChannel) send(transform(value))
        } catch(t: CancellationException) {
            // When we cancel the producer the JobCancellationException is thrown
            receiveChannel.cancel()
        }
    }
}

suspend fun consume(coroutineScope: CoroutineScope, producer: ReceiveChannel<Int>) = coroutineScope.launch {
    producer.consumeEach {
        println("Received: $it")
    }
}

fun course3Pipelining() = runBlocking {
    // Chaining together two producers. One that produces the numbers and that squares the numbers. Pipeline!
    val producer = produceNumbers(this)
    // -1-
//    val squareProducer = createSquares(this, producer)
    // -2-
//    val cubeProducer = map(this, producer) {
//        it * it * it
//    }
    // -3-
    val cubeProducer = produceNumbers(this).map2(this) {
        it * it * it
    }

    // -1-
//    consume(this, squareProducer)
    // -2-
//    consume(this, cubeProducer)
    // -3-
    consume(this, cubeProducer)


//    launch {
//        producer.consumeEach {
//            println("Received: $it")
//        }
//    }

    delay(5000)

    producer.cancel()
}
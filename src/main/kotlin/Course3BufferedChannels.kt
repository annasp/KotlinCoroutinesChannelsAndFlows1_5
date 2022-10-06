import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course3BufferedChannels() = runBlocking {
    val channel = Channel<Int>(4)

    val producer = launch {
        repeat(4) {
            println("Sending $it")
            channel.send(it)
        }
    }

    val consumer = launch {
        println("Consumer starting")
        delay(1000)
        println("Consumer started")

        for(value in channel) {
            println(" -- received $value")
        }
    }
    delay(500)
    println("Cancelling producer")
    producer.cancel()
    consumer.join()
}
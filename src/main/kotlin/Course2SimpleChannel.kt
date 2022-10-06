import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.channels.Channel

fun course2SimpleChannel() = runBlocking {
    // Int is the type of data that we want to send and read from that channel
    val channel = Channel<Int>()

    val producer = launch {
        delay(500)
        channel.send(1)
    }

    val consumer = launch {
        val data: Int = channel.receive()
        println("Received $data")
    }

    // we want these two coroutines to communicate - one way is by using a channel
    producer.join()
    consumer.join()
}
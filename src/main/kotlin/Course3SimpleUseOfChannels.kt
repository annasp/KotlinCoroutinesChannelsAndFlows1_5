import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

fun course3SimpleUseOfChannels() = runBlocking {
    val channel = Channel<Int> {}
    val jobs = mutableListOf<Job>()

    jobs.add(launch() {
        for ( x in 1..5) {
            println("Sending ${x*x}")
            channel.send(x*x)
        }
        channel.close()
    })

    jobs.add(launch {
        val value = channel.receive()
        println("Received: $value")

//        repeat(4) {
//            val value = channel.receive()
//            println("Received: $value")
//        }

        // The coroutine is waiting on the channel,
        // it's waiting to receive another item from the channel
        // and that item is never going to arrive
        // So, the call to receive blocks and the coroutine never returns
        //
//        repeat(5) {
//            val value = channel.receive()
//            println("Received: $value")
//        }

        for (value in channel) {
            println("Received: $value")
        }
    })

    jobs.forEach { it.join() }
}
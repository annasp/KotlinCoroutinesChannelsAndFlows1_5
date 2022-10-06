import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun generateIntsCourse5MultipleConsumers(): Flow<Int> = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit $value")
        emit(value++)
    }
}


fun course5MultipleConsumers() = runBlocking {
    /** Both of these collectors are collecting all the data from the flow
     * And they both start at the beginning.
     * The flow doesn't start until we subscribe to it
     * and when we subscribe to it, it starts at the beginning
    */
    val job = launch {
        generateIntsCourse5MultipleConsumers().collect {
            println("Collected (A): $it")
        }
    }

    delay(5500)

    launch {
        generateIntsCourse5MultipleConsumers().collect {
            println("Collected (B): $it")
        }
    }

    /**
     * Cancelled only the first collector.
     * If we do this with channel, if we cancel the channel
     * all the consumers would stop receiving data from that channel
     * as they all share the channel. But here we have separate flows
     * for each consumer, so we can cancel the flows independently and
     * we can manage the flows independently.
     * It's the job of a given consumer to manage its own flow and
     * that management won't affect the other consumers, the other collectors
     * of that flow 
     */
    delay(2500)
    job.cancel()
}
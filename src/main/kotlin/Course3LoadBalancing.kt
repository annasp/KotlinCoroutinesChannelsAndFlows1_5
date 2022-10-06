import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Channels provide a robust communication mechanism to allow coroutines to share data.
 * So rather using something like async and await where a single coroutine returns
 * a single piece of data that another coroutine can consume, we can use channels to
 * communicate across coroutines. We've seen that channels are a rendezvous point.
 * We see that when we do a send or receive on a channel, that send or receive is blocking.
 * We can see that channels need managing. So when we use a channel, we need to be able to set
 * the thing up, we need to be able to control how and when it is used. We've seen that
 * blocking channles might be an issue, and to get around some of these issues we can use buffered
 * channels where we can give the channel a buffer size. And we've seen that ideally we need to close channels
 * when we finish with them just to make sure that any resources the channel is using is tidied up
 * behing it.
 */

const val totalAgents = 10
const val totalWorkItems = 200

data class Work(val x: Long, val y: Long, var total: Long = 0L)

suspend fun agent(input: ReceiveChannel<Work>, output: SendChannel<Work>) {
    for (work in input) {
        work.total = work.x * work.y
        delay(work.total)
        output.send(work)
    }
}

suspend fun producer(output: SendChannel<Work>) {
    repeat(totalWorkItems) {
        output.send(Work((0L..100).random(), (0L..10L).random()))
    }
}

suspend fun consumer(input: ReceiveChannel<Work>) {
    for (work in input) {
        println("${work.x}*${work.y} = ${work.total}")
    }
}

suspend fun CoroutineScope.run() {
    /**
     * Producer is sending work to the agent, the agent is executing some of that work,
     * so it is multiplying these two numbers together and producing some output,
     * and then our single consumer is consuming all of the work that comes back.
     * So you have one producer, many agents, one consumer
     */
    val producerChannel = Channel<Work>()
    val consumerChannel = Channel<Work>()


    repeat(totalAgents) {
        launch {
            agent(producerChannel, consumerChannel)
        }
    }

    launch {producer(producerChannel)}
    launch {consumer(consumerChannel)}
}

fun course3LoadBalancing() = runBlocking {
    run()
}

private object RandomRangeSingleton : Random()

fun ClosedRange<Long>.random() = (RandomRangeSingleton.nextInt((endInclusive.toInt() + 1) - start.toInt()) + start)
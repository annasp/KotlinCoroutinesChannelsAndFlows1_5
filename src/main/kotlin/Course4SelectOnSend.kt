import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.producerNumbers(side: SendChannel<Int>) = produce<Int>() {
    for (num in 1..10) {
        delay(100)
        // Blocking send
//        send(num)
        select<Unit> {
            onSend(num) {

            }
            side.onSend(num) {}
        }
    }
    println("Done sending")
}

fun course4SelectOnSend() = runBlocking {
    val side = Channel<Int> {  }

    launch {
        side.consumeEach {
            println("Side channel consumed: $it")
        }
    }

    val producer = producerNumbers(side)

    producer.consumeEach {
        delay(500)
        println(it)
    }
}
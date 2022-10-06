import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.course4TimeOutProducerNumbers() = produce<Int>() {
    var num = 0
    while(true) {
        delay(5000)
        send(num++)
    }
}

fun course4TimeOut() = runBlocking<Unit> {
    val producer = course4TimeOutProducerNumbers()

    select {
        producer.onReceive {
            println("Consumed: $it")
        }
        onTimeout(1000) {
            println("Timed out")
        }
    }
}
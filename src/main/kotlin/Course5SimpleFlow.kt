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

fun generateIntsCourse5SimpleFlow(): Flow<Int> = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit $value")
        emit(value++)
    }
}


fun course5SimpleFlow() = runBlocking {
    val job = launch {
        generateIntsCourse5SimpleFlow().collect {
            println("Collected (A): $it")
            if(it == 5) {
                this.cancel()
            }
        }
    }

//    delay(5500)
//    // This cancels the collector and when we cancel the collector
//    // and that stops the flow
//    job.cancel()
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun course7BufferingGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7Buffering() = runBlocking {
    launch {
        // using buffer we save time...
        val time = measureTimeMillis {
            course7BufferingGenerateInts()
                .take(5)
                .buffer(5)
                .collect() {
                    delay(500)
                    println("Collected $it")
                }
        }
        println("It took: ${time}ms")
    }
}
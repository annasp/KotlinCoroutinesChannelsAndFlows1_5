import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun course7ConflationGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7Conflation() = runBlocking {
    launch {
        course7ConflationGenerateInts()
            .take(5)
            .conflate() // It will throw away values in the flow that we don't have time to collect. This is useful if the emitter emits data very quickly, and we don't care about of some data
            .collect() {
                delay(300)
                println("Collected $it")
            }
    }
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.measureTimeMillis

fun course7ZippingFlowsGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7ZippingFlowsGenerateRandomInts() = flow<Int> {
    val rnd = Random(100)
    while(true) {
        val value = rnd.nextInt(20)
        delay(1000)
        println("emit random $value")
        emit(value)
    }
}

fun course7ZippingFlows() = runBlocking {
    launch {
        course7ZippingFlowsGenerateInts()
            .take(5)
            .zip(course7ZippingFlowsGenerateRandomInts()) { a, b -> "$a * $b"}
            .collect {
                println("Collected $it")
            }
    }
}
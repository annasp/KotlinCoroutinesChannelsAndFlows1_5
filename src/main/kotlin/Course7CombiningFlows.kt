import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.measureTimeMillis

fun course7CombiningFlowsGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7CombiningFlowsGenerateRandomInts() = flow<Int> {
    val rnd = Random(100)
    while(true) {
        val value = rnd.nextInt(20)
        delay(1000)
        println("emit random $value")
        emit(value)
    }
}

fun course7CombiningFlows() = runBlocking {

    var startTime = System.currentTimeMillis()

    val job = launch {
        course7CombiningFlowsGenerateInts()
            .take(5)
            .zip(course7ZippingFlowsGenerateRandomInts()) { a, b -> a * b }
            .collect {
                println("zipped at ${System.currentTimeMillis() - startTime}: $it")
            }
    }

    job.join()

    startTime = System.currentTimeMillis()

    launch {
        course7CombiningFlowsGenerateInts()
            .combine(course7ZippingFlowsGenerateRandomInts()) { a, b -> a * b}
            .collect {
                println("combined at ${System.currentTimeMillis() - startTime}: $it")
            }
    }
}
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun course7FlatteningFlowsGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7FlatteningFlowsGenerateString(ndx: Int) = flow {
    emit("$ndx as string-1")
    delay(500)
    emit("$ndx as string-2")
}

@OptIn(FlowPreview::class)
fun course7FlatteningFlows() = runBlocking {
    val job = launch {
        val f: Flow<Flow<String>> = course7FlatteningFlowsGenerateInts()
            .take(5)
            .map { course7FlatteningFlowsGenerateString(it) }

        val f1: Flow<String> = f.flattenConcat()

        f1.collect { println("Collected $it") }
    }

    job.join()

    launch {
        val f: Flow<String> = course7FlatteningFlowsGenerateInts()
            .take(5)
            .flatMapConcat { course7FlatteningFlowsGenerateString(it) }

        f.collect { println("Collected $it") }
    }
}
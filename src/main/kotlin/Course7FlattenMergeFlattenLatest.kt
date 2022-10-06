import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun course7FlattenMergeFlattenLatestGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        println("emit $value")
        emit(value++)
    }
}

fun course7FlattenMergeFlattenLatestGenerateString(ndx: Int) = flow {
    emit("$ndx as string-1 in ${Thread.currentThread().name}")
    delay(500)
    emit("$ndx as string-2 in ${Thread.currentThread().name}")
}

@OptIn(FlowPreview::class)
fun course7FlattenMergeFlattenLatest() = runBlocking {
    val job = launch {
        val f: Flow<String> = course7FlattenMergeFlattenLatestGenerateInts()
            .take(5)
            .flatMapConcat { course7FlattenMergeFlattenLatestGenerateString(it) } // merges synchrnously

        f.collect { println("Collected $it") }
    }

    job.join()

    println()
    println()
    println()

    launch {
        val f: Flow<String> = course7FlattenMergeFlattenLatestGenerateInts()
            .take(5)
            .flatMapMerge { course7FlattenMergeFlattenLatestGenerateString(it) } // The coroutine runs concurrenlty here

        f.collect { println("Collected $it") }
    }
}
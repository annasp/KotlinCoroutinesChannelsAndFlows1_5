import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course9TransparentCompletionGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(100)
        emit(value++)
        check( value < 3) { "emitter" }
    }
}

fun course9TransparentCompletion() = runBlocking {
    launch {
        course9TransparentCompletionGenerateInts()
            .take(5)
            .onEach {
//                check( it < 2 ) { "Collector" }
                println("Collected $it")
            }
            .onCompletion { cause -> println("Done $cause") }
            .catch { println("Catch ${it.message}")}
            .collect {

            }
    }
}
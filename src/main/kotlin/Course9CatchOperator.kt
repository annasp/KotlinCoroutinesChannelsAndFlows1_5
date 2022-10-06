import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course9CatchOperatorGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(100)
        emit(value++)
        check( value < 3) { "emitter" }
    }
}

fun course9CatchOperator() = runBlocking {
    launch {
        try {
            course9CatchOperatorGenerateInts()
                .take(5)
                .onEach {
                    check( it < 2 ) { "Collector" }
                    println("Collected $it")
                }
                .catch { println("Catch ${it.message}")}
                .collect {

                }
        } finally {
            println("Completed")
        }
    }
}
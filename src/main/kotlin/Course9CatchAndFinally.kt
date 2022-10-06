import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course9catchAndFinallyGenerateInts() = flow {
    var value = 0
    while(true) {
        check( value < 2) { "emitter" }
        delay(100)
        emit(value++)
    }
}

fun course9catchAndFinally() = runBlocking {
    launch {
        try {
            course9catchAndFinallyGenerateInts()
                .take(5)
                .collect {
                    check( it < 3 ) { "Collector" }
                    println("Collected $it")
                }
        } catch (t: Throwable) {
            println(t.message)
        } finally {
            println("Completed")
        }
    }
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course9TransparencyGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(100)
        emit(value++)
        check( value < 3) { "emitter" }
    }
}

fun <T> Flow<T>.flowWithCatch() = flow {
    collect { emit(it) }
// Don't catch exceptions inside a flow
//    try {
//        collect { emit(it) }
//    } catch(t: Throwable) {
//        println("FlowWithCatch ${t.message}")
//    }
}

fun course9Transparency() = runBlocking {
    launch {
        try {
            course9TransparencyGenerateInts()
                .flowWithCatch()
                .take(5)
                .collect {
                    check( it < 2 ) { "Collector" }
                    println("Collected $it")
                }
        } catch (t: Throwable) {
            println(t.message)
        } finally {
            println("Completed")
        }
    }
}
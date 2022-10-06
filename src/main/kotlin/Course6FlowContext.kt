import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun course6FlowContextGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emitter $value: ${Thread.currentThread().name}")
        emit(value++)
    }
    // Error. We can't change the context within the flow itself
//    withContext(Dispatchers.Default) {
//        while(true) {
//            delay(1000)
//            println("emitter $value: ${Thread.currentThread().name}")
//            emit(value++)
//        }
//    }
}.flowOn(Dispatchers.Default) // used to change the context of flow. Run this on the flow itself not on the collector.

fun course6FlowContext() = runBlocking {

    launch {
        course6FlowContextGenerateInts()
            .collect {
                delay(1500)
                println("Collector $it: ${Thread.currentThread().name}")
            }
    }
}
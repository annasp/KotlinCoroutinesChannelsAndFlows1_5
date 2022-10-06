import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course8StateFlowGenerateInts() : Flow<Int> = flow {
    var value = 0
    while(true) {
        delay(500)
        println("emit $value")
        emit(value++)
    }
}

fun course8StateFlow() = runBlocking {

    val flow = course8StateFlowGenerateInts().stateIn(this)

    launch {
        flow.collect() {
            println("Collector (A) $it")
        }
    }

    delay(2000)

    launch {
        println("Collector (B) before collect ${flow.value}")
        flow.collect() {
            println("Collector (B) $it")
        }
    }
}
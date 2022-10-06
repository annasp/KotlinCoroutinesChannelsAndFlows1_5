
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course8MutableStateFlow() = runBlocking {
    val counter = Course8MutableStateFlowCounter()

    launch {
        while(true) {
            counter.increment()
            delay(1000)
        }
    }

    launch {
        while(true) {
            counter.increment()
            delay(100)
        }
    }

    delay(500)

    launch {
        println("Collector (A) before: ${counter.counter.value}")

        counter.counter.collect {
            delay(100)
            println("Collector (A): $it")
        }
    }
}

class Course8MutableStateFlowCounter {
    private val _counter = MutableStateFlow(0)

    val counter = _counter.asStateFlow()

    fun increment() {
        _counter.update { it + 1 }
    }
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.measureTimeMillis

fun course8MutableSharedFlow() = runBlocking {
    val counter = Course8MutableSharedFlowCounter()

    launch {
        var value = 0
        while (true) {
            counter.produce(value++)
            delay(200)
        }
    }

    launch {
        counter.counter.collect {
            delay(100)
            println("Collected (A): $it")
        }
    }

    delay(1000)

    launch {
        counter.counter.collect {
            delay(100)
            println("Collected (B): $it")
        }
    }
}

class Course8MutableSharedFlowCounter {
    private val _counter = MutableSharedFlow<Int>(5)

    val counter = _counter.asSharedFlow()

    suspend fun produce(value: Int) {
        _counter.emit(value)
    }
}
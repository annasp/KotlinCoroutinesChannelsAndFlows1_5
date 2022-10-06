import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
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

fun course8SharedFlowGenerateInts() = flow<Int> {
    var value = 0
    while(true) {
        delay(100)
        emit(value++)
    }
}

fun course8SharedFlow() = runBlocking {

    val flow = course8SharedFlowGenerateInts()
        .shareIn(
            this,
            SharingStarted.WhileSubscribed(),
            0
        )

    launch {
        flow.collect {
            println("Collector (A) $it")
        }
    }

    delay(2000)

    launch {
        flow.collect {
            println("Collector (B) $it")
        }
    }
}
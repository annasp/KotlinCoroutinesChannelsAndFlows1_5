import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6DebounceGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        repeat(10) {
            println("emit: $value")
            emit(value)
        }
        value++
    }
}

fun course6Debounce() = runBlocking {

    launch {
        course6DebounceGenerateInts()
                // needs tuning
            .debounce(1000)
            .collect {
                println("Collected: $it")
            }
    }
}
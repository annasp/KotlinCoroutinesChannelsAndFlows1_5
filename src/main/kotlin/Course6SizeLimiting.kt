import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6SizeLimitingGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6SizeLimiting() = runBlocking {

    launch {
        course6SizeLimitingGenerateInts()
//            .take(3)
            .takeWhile { it < 7 }
            .collect {
                println("Collected: $it")
            }
    }
}
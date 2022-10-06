import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6MappingGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6Mapping() = runBlocking {

    launch {
        course6MappingGenerateInts()
            .map { "$it * 2" }
            .collect {
                println("Collected: $it")
            }
    }
}
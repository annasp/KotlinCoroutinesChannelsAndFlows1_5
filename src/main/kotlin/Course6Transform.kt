import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6TransformGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6Transform() = runBlocking {

    launch {
        course6FilteringGenerateInts()
            .transform {
                if (it % 2 == 0)
                    emit(it * 1)
                    emit(it * 2)
                    emit(it * 3)
            }
            .collect {
                println("Collected: $it")
            }
    }
}
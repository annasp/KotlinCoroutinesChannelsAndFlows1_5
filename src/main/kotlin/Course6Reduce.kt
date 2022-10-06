import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6ReduceGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(3000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6Reduce() = runBlocking {

    launch {
        /**
         * Reduce is a terminal operation, so reduce starts the flow
         * executing, and then reduce doesn't get called until we've emitted
         * the first two values
         */
        course6ReduceGenerateInts()
            .reduce {a, b ->
                println("a: $a")
                println("b: $b")
                a + b
            }
    }
}
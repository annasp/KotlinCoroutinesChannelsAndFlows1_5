import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6TerminatingReduceGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(3000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6TerminatingReduce() = runBlocking {
    launch {
        val res = course6TerminatingReduceGenerateInts()
            .drop(1)
            .take(5)
            .fold(1) {a, b ->
            println("a: $a")
            println("b: $b")
            a * b
            }
        println("Result is: $res")
    }

//    launch {
//        /**
//         * Reduce is a terminal operation, so reduce starts the flow
//         * executing, and then reduce doesn't get called until we've emitted
//         * the first two values
//         */
//        val res = course6TerminatingReduceGenerateInts()
//            .drop(1)
//            .take(5)
//            .reduce {a, b ->
//                println("a: $a")
//                println("b: $b")
//                a * b
//            }
//        println("Result is: $res")
//    }
}
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6FilteringGenerateInts() = flow {
    var value = 0
    while(true) {
        delay(1000)
        println("emit: $value")
        emit(value++)
    }
}

fun course6Filtering() = runBlocking {

    launch {
        // returns the first item of the flow and then terminates the flow
        // first() -> is both a terminal and a terminating operator on the flow
        val res: Int = course6FilteringGenerateInts()
            .first()

        println("First: $res")
    }

//    launch {
//        course6FilteringGenerateInts()
//            .filter { it % 2 == 0 }
////            .filterNot { it % 2 == 0 }
//            .map { it * 2 }
//            .collect {
//                println("Collected: $it")
//            }
//    }
}
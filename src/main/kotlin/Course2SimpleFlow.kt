import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

// Emit values on one side and collect values on the other side
fun generateInts() = flow {
    repeat(10) {
        delay(1000)
        emit(it)
    }
}

fun course2SimpleFlow() = runBlocking {
    generateInts().collect {
        println("Collected: $it")
    }
}
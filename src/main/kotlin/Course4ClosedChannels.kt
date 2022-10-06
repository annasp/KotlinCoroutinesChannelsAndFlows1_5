
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer3() = produce<String>() {
    var count = 100

    send("From producer ${count++}")
}

fun CoroutineScope.producer4() = produce<String>() {
    var count = 200
    send("From producer ${count++}")
}

/** Two producers producing messages
 * and then a selector that selects from those producers
 * when the messages are available
 */
fun course4SClosedChannels() = runBlocking {
    val p3 = producer3()
    val p4 = producer4()

    // 15 values are printed. It is getting the values from
    // both selectors, but it is getting the value from
    // the first selector preferentially here. But we are not
    // losing data
    repeat(15) {
        val result = select<String> {
            // Producer 1 is selected preferentially because it is the first in the list
            p3.onReceiveCatching {
//                it.getOrThrow()
                it.getOrNull() ?: "Channel 1 is closed"
            }
            p4.onReceiveCatching {
//                it.getOrThrow()
                it.getOrNull() ?: "Channel 2 is closed"
            }
        }

        println(result)
    }

    println("End main")
    p3.cancel()
    p4.cancel()
}

/** Output
 * From producer 100
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
Channel 1 is closed
End main
 */
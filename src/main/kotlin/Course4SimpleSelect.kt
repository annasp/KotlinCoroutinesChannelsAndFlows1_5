import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer1() = produce<String>() {
    var count = 100

    while(true) {
        // delay(200)
        send("From producer ${count++}")
    }
}

fun CoroutineScope.producer2() = produce<String>() {
    var count = 200

    while(true) {
        // delay(300)
        send("From producer ${count++}")
    }
}

/** Two producers producing messages
 * and then a selector that selects from those producers
 * when the messages are available
 */
fun course4SimpleSelect() = runBlocking {
    val p1 = producer1()
    val p2 = producer2()

    // 15 values are printed. It is getting the values from
    // both selectors, but it is getting the value from
    // the first selector preferentially here. But we are not
    // losing data
    repeat(15) {
        select<Unit> {
            // Producer 1 is selected preferentially because it is the first in the list
            p1.onReceive {
                println(it)
            }
            p2.onReceive {
                println(it)
            }
        }
    }

    println("End main")
    p1.cancel()
    p2.cancel()
}
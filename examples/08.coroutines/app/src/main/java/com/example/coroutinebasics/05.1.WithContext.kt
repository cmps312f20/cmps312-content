import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
 // use withContext to change context and switch between dispatchers
    runBlocking {
       launch(Dispatchers.Default) {
           println("first Context , ${coroutineContext}")
           withContext(Dispatchers.IO){
            println("second context , ${coroutineContext}")
           }
           println("third Context , ${coroutineContext}")
       }
    }
}
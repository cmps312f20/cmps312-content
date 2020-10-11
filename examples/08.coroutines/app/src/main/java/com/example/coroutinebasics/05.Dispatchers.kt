import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch (Dispatchers.Default){
            println("default. Thread ${Thread.currentThread().name}")
        }

        launch (Dispatchers.IO){
            println("io. Thread ${Thread.currentThread().name}")
        }

        launch (Dispatchers.Unconfined){
            println("unconfined. Thread ${Thread.currentThread().name}")
        }

//        launch (Dispatchers.Main){
//            println("main. Thread ${Thread.currentThread().name}")
//        }

        launch (newSingleThreadContext("mythread")){
            println("newSingleThread. Thread ${Thread.currentThread().name}")
        }
    }
}
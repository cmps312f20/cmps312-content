package lambdas

fun main() {
    val isEven: (Int) -> Boolean = { i -> i % 2 == 0 }
    val list = listOf(1, 2, 3, 4)
    val evenNumberInList = list.any(isEven)
    val evens = list.filter(isEven)

    println("Is there any even number: $evenNumberInList")
    println("Even numbers: $evens")

    val names = arrayOf("joe", "ann", "molly", "dolly")
    val sorted = names.sortedBy { name -> name.length }
    val sorted2 = names.sortedBy { it.length }
    println(sorted)
}
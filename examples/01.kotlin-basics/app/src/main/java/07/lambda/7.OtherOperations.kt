package lambda

fun String.isPhoneNumber() =
    length == 7 && all { it.isDigit() }

fun main() {
    println( "2345678 is phone number: ${"2345678".isPhoneNumber()}" )

    val people = listOf(
            Person("Ali", 31),
            Person("Sara", 29),
            Person("Khadija", 31))

    println(people.filter { it.age > 30 })
    println(people.map { it.name })

    println(people.maxByOrNull { it.age })

    println(people.any { it.name.startsWith("A") })

    println(people.find { it.age > 30 })
    println(people.firstOrNull { it.age > 30 })

    val (even, odd) = listOf(1, 2, 3, 4).partition { it % 2 == 0 }
    println(even)
    println(odd)

    val students = listOf("Ali", "Sara", "Fatima")
    val grades = listOf(80.5, 90, 75.5)
    val result = students.zip(grades)
    println(result)

    println(people.groupBy { it.age })

    val listOfList = listOf(
        listOf("one", "two", "three"),
        listOf("four", "five", "six"),
        listOf("seven", "eight")
    )
    val singleList = listOfList.flatMap { it }
    println(singleList)
    //Tranform a list to a string
    println(singleList.joinToString())
}

package _8Lambdas.workingWithCollections

data class Person(val name: String, val age: Int)

fun main(args: Array<String>) {
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
    val grades = listOf(80, 90, 75)
    val result = students.zip(grades)
    println(result)

    println(people.groupBy { it.age })

    println(listOf("abc", "123").map {
        it.toCharArray().toList() })

    println(listOf("abc", "123").flatMap {
        it.toCharArray().toList() })
}

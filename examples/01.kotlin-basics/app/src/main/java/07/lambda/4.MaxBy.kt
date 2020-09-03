package lambda

data class Person(val name: String, val age: Int)

fun main() {
    val people = listOf(
            Person("Ali", 29),
            Person("Ahmed", 31))

    //Version 1 - best
    people.forEach { println(it) }
    // Version 2 -using a member reference (i.e., referencing println function)
    people.forEach(::println)

    //Version 1 - best
    var oldestPerson = people.maxByOrNull { it.age }
    //Version 2 -  using a member reference (i.e., referencing age property of person)
    oldestPerson = people.maxByOrNull(Person::age)

    println("Oldest Person: $oldestPerson")
}
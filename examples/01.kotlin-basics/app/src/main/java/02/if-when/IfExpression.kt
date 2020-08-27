package controlFlow

fun main() {
    val age = 17

    val canVote = if (age > 17) "Yes, can vote" else "Cannot vote yet"

    println(canVote)
}
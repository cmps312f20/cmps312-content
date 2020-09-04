package collections

fun main() {
    val languages = mapOf(
        1 to "Python",
        2 to "Kotlin",
        3 to "Java"
    )

    for ((key, value) in languages) {
        println("$key => $value")
    }
}
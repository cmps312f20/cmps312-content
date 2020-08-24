package controlFlow

fun main() {

    // Defining variables
    val age = 20

    // CASE 1: Using 'if' as a Statement
    var ageCategory : String
    if (age < 18) {
        ageCategory = "Teenager"
    } else {
        ageCategory = "Young Adult"
    }

    val ageCategory3 = when {
        (age < 18) -> "Teenager"
        else -> "Young Adult"
    }

    // Printing results
    println("> $ageCategory")

    // --------------------------------------------------

    // CASE 2: Using 'if' as an Expression
    // Note: The 'else' case must be present.
    val ageCategory2 = if (age < 18) {
        "Teenager"
    } else {
        "Young Adult"
    }

    // Printing results
    println("> $ageCategory2")

}
package variables

fun main() {
    //To make a variable nullable simply add ? to the data type
    var name : String? = "Ahmed"
    name = null
    //name = "Ali"

    val length = if (name != null) name.length else 0
    // Better syntax is to use the Elvis operator (?:)
    val len = name?.length ?: 0

    println("The length of \"$name\" is $length")
}
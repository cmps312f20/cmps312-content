package functions

// functions with varargs parameters
fun varargExample(vararg names: Int) {
    println("Argument has ${names.size} elements")
}

fun main() {
    varargExample() // => Argument has 0 elements
    varargExample(1) // => Argument has 1 elements
    varargExample(1, 2, 3) // => Argument has 3 elements
}

package `07`.lambdas

fun main() {
    val sb = StringBuilder()
    val str = with (sb) {
        appendln("Alphabet: ")
        for (c in 'a'..'z') {
            append(c)
            append(", ")
        }
        toString()
    }

    println(str)
}
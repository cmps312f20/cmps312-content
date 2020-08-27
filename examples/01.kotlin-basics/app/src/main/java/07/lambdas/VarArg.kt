package lambdas

fun max(vararg numbers: Int) = numbers.reduce { max, e -> if(e > max) e else max }

fun main() {
    println(max(1, 2))
    println(max(7, 3, 11))

    val values = intArrayOf(1, 22, 4)
    println(max(4, 5, *values, 8))
}

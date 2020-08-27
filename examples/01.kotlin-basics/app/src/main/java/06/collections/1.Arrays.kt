package collections

fun main() {
    val names: Array<String> = emptyArray()
    val colors: Array<String> = arrayOf("Red", "Green", "Blue")
    colors[2] = "Pink"
    val nulls: Array<String?> = arrayOfNulls(10)

    val numbers: Array<Int> = emptyArray()
    val nums: Array<Int> = arrayOf(2, 3, 4)
    val nullNums: Array<Int?> = arrayOfNulls(10)

    colors.forEach { println(it) }
}
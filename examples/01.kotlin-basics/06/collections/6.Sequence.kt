package collections

fun main() {
    // Sequences represent lazily-evaluated collections
    val numSequence = generateSequence(1, { it + 1 })
    val nums = numSequence.take(10).toList()
    println(nums) // => [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    // Convert list to a sequence to enable lazy evaluation
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.asSequence()
            .map { it * 2 } // Lazy
            .filter { it % 2 == 0 } // Lazy
            .reduce(Int::plus) // Terminal (eager)
    println(sum) // 30

}
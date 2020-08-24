package variables

fun main(args: Array<String>) {
    // CASE 1: Declaring a nullable and non-nullable variable
    val iCannotBeNull = "Not Null"
    val iCanBeNull: String? = null

    // The following produces an error:
    //iCannotBeNull = null

    // CASE 2: Checking for null using 'if'
    if (iCanBeNull != null) {
        println("It is not null")
    }

    // CASE 3: Checking for null using the 'let' operator
    // The 'let' block only executes if 'iCanBeNull' is not null
    iCanBeNull?.let {
        // 'it' is the non-null value stored in 'iCanBeNull'
        println("$it is not null")
    }

    // CASE 4: Calling methods of null and non-null variables
    val measure = iCanBeNull?.length // Only executes if 'iCanBeNull' is not null
    val size = iCannotBeNull.length // It is guaranteed that length will not be null

    // Notice the below statement does not crash the program.
    measure.toString()

    // CASE 5: Using the elvis operator to still get a value in case 'iCanBeNull' is null
    val distance = iCanBeNull?.length ?: -1
}
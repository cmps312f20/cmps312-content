package controlFlow

fun main(args: Array<String>) {
    val month = 8

    // when expression
    val season = when (month) {
        12, 1,2  -> "Winter"
        in 3..5 -> "Spring"
        in 6..8 -> "Summer"
        in 9..11 -> "Autumn"
        else -> "Invalid Month"
    }

    println("Season for month $month is $season")

    // 'if' vs 'when'
    println(" ")
    val number = 20

    // If
    if (number % 2 == 0) {
        println("$number is even")
    } else {
        println("$number is odd")
    }

    // when: Notice how the 'when' has no argument
    when {
        number % 2 == 0 -> println("$number is even")
        else -> println("$number is odd")
    }
}

/*
Equivalent switch in Java:

        switch (month) {
            case 12:
            case 1:
            case 2:
                season = "Winter";
                break;
            case 3:
            case 4:
            case 5:
                season = "Spring";
                break;
            case 6:
            case 7:
            case 8:
                season = "Summer";
                break;
            case 9:
            case 10:
            case 11:
                season = "Autumn";
                break;
            default:
                season = "Not a valid month";

        }
 */
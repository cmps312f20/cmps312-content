package `07`.lambdas

fun isPhoneNumber(s: String) : Boolean {
    val isAllDigit = s.all { it.isDigit() }

    /*for(num in s) {
       if (!num.isDigit()) {
          isAllDigit = false
       }
    } */

    return s.length == 7 && s.all { it.isDigit() }
}


/*
fun isPhoneNumber(s: String) =
        s.length == 7 && s.all { it.isDigit() }
*/

fun String.isPhoneNum() =
        length == 7 && all { it.isDigit() }

fun main() {

    println( isPhoneNumber("1234567"))

    //println( "2345678".isPhoneNum() )
}
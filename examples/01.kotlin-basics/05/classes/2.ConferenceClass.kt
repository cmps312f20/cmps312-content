package classes

class Conference(val name: String,
                 val city: String,
                 val isFree: Boolean = false) {
    var fee : Double = 0.0

    // Secondary Constructor
    constructor(name: String,
                city: String,
                fee: Double) : this(name, city) {
        this.fee = fee
    }
}

fun main() {
    val conference = Conference("Kotlin Conf.", "Doha")
    println(conference)
}

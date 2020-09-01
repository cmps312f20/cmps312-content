package `05`.classes

import java.lang.IllegalArgumentException

// Class can have 1 or many constructors: initialize an object
open class Person(var firstname: String, val lastname: String, var age: Int) {
    var phone: String = ""

    //Secondary constructor
    constructor(firstname: String, lastname: String, age: Int, phone: String) : this(firstname, lastname, age) {
        if (phone.isEmpty() || phone.length != 7)
            throw IllegalArgumentException("$phone is an invalid phone number")

        this.phone = phone
    }

    var id = 0
    //val fullname: String = ""

    val fullname: String
        get() = "$firstname $lastname"

    fun isUnderAge() = age < 18

    init {
        println("Init it auto-called after the default constructor")
        id = (1..100).shuffled().first()

        if (age <= 0)
            throw IllegalArgumentException("$age is invalid age")
    }

    override fun toString() = "$fullname is $age years old"
}

class Student(firstname: String, lastname: String, age: Int, val gpa: Double) : Person(firstname, lastname, age) {
    override fun toString() = "${super.toString()}. Gpa is: $gpa"
}

fun main() {
    val student1 = Student("Ali", "Faleh", 22, 3.6)
    println(student1)
    println(student1.fullname)
}
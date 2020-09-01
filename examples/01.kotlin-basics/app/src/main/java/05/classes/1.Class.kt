package classes

fun String.isPhoneNum() =
    length == 7 && all { it.isDigit() }
/*
    By default, all classes in kotlin are 'final'.
    Therefore, to allow a class from being inherited,
    we must mark it the class as 'open.'
 */
/*
    - We must explicitly marks as 'open' the methods and variables from
      the base class that are allowed to be overriden.
    - A class property or method marked as 'open' cannot have a visibility of 'private'
    - If a class is 'final' then declaring properties or methods as 'open' has no effect.
 */
// You can set default values, if necessary

// Static methods =
/*
 class Calculator {
     static int add(int x, int y) { return x + y; }
 }

 main() {
   //Calculator calculator = new Calculator();
   //calculator.add(10, 20);
   int sum = Calculator.add(10, 20);
 }
*/

open class Person(val firstName: String,
             val lastName: String, val age: Int) {

    var id : Int = 0
    var mobile : String = ""

    // Secondary constructor must call the primary constructor with "this".
    constructor(firstName: String,
                lastName: String,
                age: Int, mobile: String) : this(firstName, lastName, age) {
        this.mobile = mobile;
    }

    val fullName: String
        get() = "$firstName $lastName"

    // !! means access it as non-null asserting that it is ok to do so
    fun isUnderAge() = age < 18

    override fun toString() = "$firstName $lastName. Age $age"

    // The primary constructor cannot contain code, so use the init block for initialization.
    init {
            // Initialization code goes here such as auto-setting the id
            id = (1..100).shuffled().first()
            //Validation
            if (age <=0)
                throw IllegalArgumentException("$age is invalid age")
    }
}

/*
    - To inherit from a class, write the name of the base class after a colon.
    - In terms of order, the base class is initialized before the child class.
    - There are two things to keep in mind when inheriting:
        1. If the child class has a primary constructor, then the parent class
            must be initialized using the parameters of the primary constructor.
        2. If the child class has no primary constructor, then each secondary
            constructor has to initialize the base type with using the 'super' keyword.
 */
class Student(firstName: String,
             lastName: String,
             age: Int,
             val gpa: Double
            ) : Person(firstName, lastName, age) {

    /*
    - To override a method from the base class, use the keyword 'override'
    - Use the 'super' keyword to call the implementation of the parent class.
   */
    override fun toString() = "${super.toString()}. GPA: ${gpa}"

    /* Kotlin classes do not have "static" properties or methods.
       So we must use a "companion object" instead.
       The companion objects allows the declaration of methods and properties that
       belong to the class. They can be accessed by using only the class name.
       No need to create an instance of the class
     */
    companion object {
        val university = "Qatar University"
        val city = "Doha"
        fun getCountry() = "Qatar"
    }
}

fun main() {
    println(Student.university)
    println(Student.city)
    println(Student.getCountry())



    return

    val person = Person("Ali", "Faleh", -25, "1234567")
    println(person)

    val student1 = Student("Fatima", "Saleh", 23, 3.4);
    println("> Full name: ${student1.fullName}")
    println("> isUnderAge: ${student1.isUnderAge()}")
    println("> toString(): $student1")

    println("Studies at ${Student.university} in ${Student.city} ${Student.getCountry()}")
}


/*


// ----------------------------
// Creating a class
// ----------------------------
// In its simples form, a class has no body, no constructor(s), and no curly braces.
class SimplestClass

// A Kotlin class can contain: Constructor(s), init block(s), functions, properties, nester classes,
// inner classes, and object declaration

// ----------------------------
// Primary Constructor
// ----------------------------
// The primary constructor is located in the class header and it is created using the keyword "constructor".
class Person constructor(name: String)

// The keyword "constructor" can be omitted if no annotation or visibility modifier is provided.
class Animal(name: String)

// The keyword "constructor" is required because we are assigning a visibility modifier.
class Car private constructor(name: String)
// class ClassExample3 private (name: String) <--- This will cause an error

// The primary constructor cannot contain code, so use the init block for initialization.
class Department(name: String) {
    init {
        // Initialization code goes here
    }
}

// A class can have more than one init blocks. They execute in the order they appear.
class Desk(name: String) {
    init {
        println("This statement prints first.")
    }

    init {
        println("This statements prints second.")
    }
}

// The parameters of a class can only be used in the init block or to initialize properties.
class Computer(name: String) {
    val nameLength = name.length

    init {
        println("Hello $name")
    }
}

// We can assign default values to the constructor's parameters
// If not values is passed when creating a class instance, the default value will be assigned.
class Planet(name: String = "Earth")

// To set the constructor's parameters as class properties, set the parameters as either "val" or "var".
class Shoe(var name: String)

// ----------------------------
// Secondary Constructor
// ----------------------------
// A class can have primary and secondary constructor.
class City(name: String) {

    // Secondary constructor must call the primary constructor with "this".
    constructor(name: String, size: Double) : this(name) {
        // Code here
    }
}

// A class can have multiple secondary constructor
class School(name: String) {

    // Secondary constructor must delegate the primary constructor with "this" keyword
    // Delegation to the primary constructor happens first, so all the init blocks
    // are executed before the execution of the secondary constructor.
    constructor(name: String, size: Double) : this(name) {
        // Code here
    }
}
 */
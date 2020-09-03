package lambda

class MenuItem {
    var name: String = ""
    var price: Double = 0.0
    var calories: Int = 0

    override fun toString() = "name: $name, price: $price, calories: $calories"
}

// This example illustrates the use of: with, apply and also
// Alternative ways of initalizing a menu item.
fun initializeMenuItem(): MenuItem {
    val menuItem = MenuItem()
    menuItem.name = "Big Mac Meal"
    menuItem.price = 22.95
    menuItem.calories = 1120
    return menuItem
}

fun initializeMenuItem1(): MenuItem {
    val menuItem = MenuItem()
    with(menuItem) {
        name = "Big Mac Meal"
        price = 22.95
        calories = 1120
    }
    return menuItem
}

//Best way of doing so
fun initializeMenuItem2() =
        MenuItem().apply {
            name = "Big Mac Meal"
            price = 22.95
            calories = 1120
        }

fun initializeMenuItem3() =
        MenuItem().also {
            it.name = "Big Mac Meal"
            it.price = 22.95
            it.calories = 1120
        }

fun main() {
    val menuItem = initializeMenuItem2()
    menuItem.toString().also(::println)
}
package lambdas

data class Player(val name: String)

class Team(val name: String,
           val players: MutableList<Player> = mutableListOf()) {

    fun addPlayers(vararg people: Player) = players.addAll(people)

    fun removePlayer(player: Player) {
        if (players.contains(player)) {
            players.remove(player)
        }
    }
}

//operator fun Team.iterator() : Iterator<Player> = players.iterator()

fun main() {
    val team = Team("Warriors")
    team.addPlayers(
        Player("Curry"),
        Player("Thompson"),
        Player("Durant"),
        Player("Green"),
        Player("Cousins")
    )

    //Old fashion
    for (player in team.players) {
        println(player)
    }

    //More concise way
    team.players.forEach(::println)

    println(team.players.any { it.name == "Curry" })
    println(team.players.map { it.name }.sorted().joinToString())
}

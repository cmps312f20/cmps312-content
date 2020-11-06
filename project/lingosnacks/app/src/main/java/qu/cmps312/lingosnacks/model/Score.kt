package qu.cmps312.lingosnacks.model

data class Score (val scoreId: String, val uid: String, val gameName: String,
                  val score: Int, val outOf: Int, val doneOn: String)
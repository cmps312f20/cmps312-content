package qu.cmps312.lingosnacks.model
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    val uid : String = "",
    val firstName : String,
    val lastName : String,
    val email : String,
    val password : String,
    val role : String,
    val photoUri : String = ""
) {
    override fun toString()
            = "${firstName.trim()} ${lastName.trim()} - ${email.trim()}".trim()
}
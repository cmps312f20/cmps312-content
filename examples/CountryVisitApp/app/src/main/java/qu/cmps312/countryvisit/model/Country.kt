package qu.cmps312.countryvisit.model
import kotlinx.serialization.Serializable

@Serializable
class Country(
    val code: String,
    val name: String,
    val continent: String
)
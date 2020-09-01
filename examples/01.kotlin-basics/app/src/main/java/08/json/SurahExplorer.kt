package json
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    val englishName : String,
    val ayaCount : Int,
    val type: String
)

fun main() {
    val filePath = "data/surahs.json";
    // Read file content
    val bufferedReader = File(filePath).bufferedReader()
    val fileContent = bufferedReader.readText()
    //println(fileContent)

    val surahs = Json.decodeFromString<List<Surah>>(fileContent)
    println(surahs)

    val ayatTotal = surahs.sumBy { it.ayaCount }
    println("Total number of Ayat: $ayatTotal")

    val countByType = surahs.groupingBy { it.type }.eachCount()
    println("Number of Surahs: $countByType")

    val ayaCountByType = surahs.groupingBy { it.type }.fold(0) { count, surah -> count + surah.ayaCount }
    println("Aya count by surah type: $ayaCountByType")

    /*
val fatiha = Surah(1, "الفاتحة", "Al-Fatiha", 7, "Meccan")
println(fatiha)

val surahJson = Json.encodeToString(fatiha)
println(surahJson)

val surah = Json.decodeFromString<Surah>(surahJson)
println(surah)
*/
//object to a json string => Serialize/Encode
//json string to an object string => Deserialize/Decode
// json = standard data format understoof by all languages: JavaScript, Java, C#, .net

}
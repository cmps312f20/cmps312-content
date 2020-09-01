package json
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.BufferedReader
import java.io.File

@Serializable
data class Person(
    val id: Int = 0,
    val firstname: String,
    val lastname: String,
    val phones: List<String> = listOf()
)

@Serializable
data class Project(val name: String, val language: String)

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
    val bufferedReader: BufferedReader = File(filePath).bufferedReader()
    val fileContent = bufferedReader.use { it.readText() }
    println(fileContent)

    val surahs = Json.decodeFromString<List<Surah>>(fileContent)
    println(surahs)

    val ayatTotal = surahs.sumBy { it.ayaCount }
    println(ayatTotal)

    val project = Project("kotlinx.serialization", "Kotlin")
    val projectJson = Json.encodeToString(project)
    println(projectJson)

    val project2 = Json.decodeFromString<Project>(projectJson)
    println(project2)
}
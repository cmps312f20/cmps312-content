package json

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object SurahRepository {
    var surahs = listOf<Surah>()
    init {
        val filePath = "data/surahs.json";
        val fileContent = getFileContent(filePath)
        //println(fileContent)

        surahs = Json.decodeFromString(fileContent)
    }

    val totalAyat = surahs.sumBy { it.ayaCount }
    val surahCountByType = surahs.groupingBy { it.type }.eachCount()
    val ayaCountByType = surahs.groupingBy { it.type }.fold(0) { count, surah -> count + surah.ayaCount }

    private fun getFileContent(filePath: String) : String {
        // Read file content
        val bufferedReader = File(filePath).bufferedReader()
        return bufferedReader.readText()
    }
}
package json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main() {
    val fatiha = Surah(1, "الفاتحة", "Al-Fatiha", 7, "Meccan")

    //object to a json string is called Serialize/Encode
    // json = standard data format understoof by all languages: JavaScript, Java, C#, .net
    val surahJson = Json.encodeToString(fatiha)
    println("Surah Json: $surahJson")

    //json string to an object string is called Deserialize/Decode
    val surah = Json.decodeFromString<Surah>(surahJson)
    println("Surah Object: $surah")

    println("Total number of Ayat: ${SurahRepository.totalAyat}")
    println("Number of surahs by type: ${SurahRepository.surahCountByType}")
    println("Aya count by surah type: ${SurahRepository.ayaCountByType}")
}
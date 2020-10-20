package json.country

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
More info @ https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the 1st (Project) build.gradle:
    // Kotlin Serialization
    classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"

2) Add this apply plugin to the 2nd build.gradle before line "android {"
//Kotlin Serialization
apply plugin: 'kotlinx-serialization'

2) Add to dependencies of the 2nd (Module) build.gradle
    //Kotlin Serialization
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0"
*/

@Serializable
data class Country (
    // Map alpha3Code property in the json file
    // to the code property
    @SerialName("alpha3Code")
    val code: String = "",
    val name: String,
    val capital: String,
    @SerialName("region")
    val continent: String,
    @SerialName("subregion")
    val region: String,
    val population: Long,
    val area: Double = 0.0,
    val flag: String,
)
package json

import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the 1st build.gradle:
        //Added for Kotlin Serialization
        classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"

2) Add to dependencies of the 2nd build.gradle
    //Added for Kotlin Serialization
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version" // or "kotlin-stdlib-jdk8"
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC" // JVM dependency

3) Add this apply plugin to the 2nd build.gradle before line "android {"
//Added for Kotlin Serialization
apply plugin: 'kotlinx-serialization'

 */

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    val englishName : String,
    val ayaCount : Int,
    val type: String
)
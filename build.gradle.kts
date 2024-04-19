// Top-level build file where you can add configuration options common to all subprojects/modules.
object libs {
    val agp = "com.android.tools.build:gradle:7.0.4"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
    val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:1.6.10"
    val mavenPublishPlugin = "com.android.tools.build:gradle:7.0.4" // Example placeholder, replace with actual dependency
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.agp)
        classpath(libs.kotlin)
        classpath(libs.kotlinSerialization)
        classpath(libs.mavenPublishPlugin)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

/**
 * Run ./gradlew dependencyUpdates to check for new updates
 * in dependencies used.
 * More info at: https://github.com/ben-manes/gradle-versions-plugin
 */

/**
 * Decides if this version is stable or not.
 */
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return !isStable
}
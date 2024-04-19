plugins {
    `kotlin-dsl`
}

val compose_version = "1.1.0-rc01"

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
    repositories {
        google()
        mavenCentral()
    }
    tasks.register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }

}


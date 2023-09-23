buildscript {
    dependencies {
        classpath(Dependencies.Develop.Androidx.navigationSafeArgs)
        classpath("com.android.tools.build:gradle:${Dependencies.Version.versionGradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Version.versionKotlin}")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version (Dependencies.Version.versionKotlin) apply false
    id("org.jetbrains.kotlin.jvm") version (Dependencies.Version.versionKotlin) apply false
    id("com.google.dagger.hilt.android") version (Dependencies.Develop.Google.versionDaggerHilt) apply false
}

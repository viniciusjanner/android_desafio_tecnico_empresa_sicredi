buildscript {
    dependencies {
        classpath(Dependencies.Develop.Androidx.navigationSafeArgs)
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version Dependencies.Develop.Google.versionDaggerHilt apply false
}

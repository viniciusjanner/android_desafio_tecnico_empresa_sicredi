plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-parcelize")

    id("androidx.navigation.safeargs")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = Config.namespace
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    lint {
        lintConfig = file("$rootDir/android-lint.xml")
        disable.add("MissingTranslation")
        disable.add("RtlHardcoded")
        abortOnError = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // ----------------------------------------------------------------------------------------------
    // Develop
    // ----------------------------------------------------------------------------------------------
    // Androidx
    implementation(Dependencies.Develop.Androidx.appCompat)

    implementation(Dependencies.Develop.Androidx.constraintLayout)

    implementation(Dependencies.Develop.Androidx.core)
    implementation(Dependencies.Develop.Androidx.coreSplashScreen)

    implementation(Dependencies.Develop.Androidx.lifecycleRunTime)
    implementation(Dependencies.Develop.Androidx.lifecycleLiveData)
    implementation(Dependencies.Develop.Androidx.lifecycleViewModel)

    implementation(Dependencies.Develop.Androidx.multidex)

    implementation(Dependencies.Develop.Androidx.navigationFragment)
    implementation(Dependencies.Develop.Androidx.navigationUi)

    // Google
    implementation(Dependencies.Develop.Google.androidMaterial)
    implementation(Dependencies.Develop.Google.codeGson)
    implementation(Dependencies.Develop.Google.daggerHilt)
    kapt(Dependencies.Develop.Google.daggerHiltCompiler)

    // Javax
    implementation(Dependencies.Develop.Javax.javaxInject)

    // JetBrains
    implementation(Dependencies.Develop.JetBrains.coroutinesAndroid)
    implementation(Dependencies.Develop.JetBrains.coroutinesCore)

    // Others
    implementation(Dependencies.Develop.Others.facebookShimmer)
    kapt(Dependencies.Develop.Others.glideCompiler)
    implementation(Dependencies.Develop.Others.glideGlide)
}

// Hilt required
kapt {
    correctErrorTypes = true
}

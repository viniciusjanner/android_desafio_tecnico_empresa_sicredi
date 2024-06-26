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
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://5f5a8f24d44d640016169133.mockapi.io/api/\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // ----------------------------------------------------------------------------------------------
    // Modules
    // ----------------------------------------------------------------------------------------------
    implementation(project(":core"))
    testImplementation(project(":testing"))

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.gms:play-services-basement:18.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

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
    implementation(Dependencies.Develop.Androidx.swiperefreshlayout)

    // Google
    implementation(Dependencies.Develop.Google.androidMaterial)
    implementation(Dependencies.Develop.Google.daggerHilt)
    kapt(Dependencies.Develop.Google.daggerHiltCompiler)

    // JetBrains
    implementation(Dependencies.Develop.JetBrains.coroutinesAndroid)

    // Others
    implementation(Dependencies.Develop.Others.facebookShimmer)
    implementation(Dependencies.Develop.Others.glideAnnotations)
    kapt(Dependencies.Develop.Others.glideCompiler)
    implementation(Dependencies.Develop.Others.glideGlide)
    implementation(Dependencies.Develop.Others.glideOkhttp)
}

// Hilt required
kapt {
    correctErrorTypes = true
}

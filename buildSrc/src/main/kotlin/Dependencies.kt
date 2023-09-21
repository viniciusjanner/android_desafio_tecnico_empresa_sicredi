object Dependencies {

    object Develop {

        object Androidx {
            val appCompat = "androidx.appcompat:appcompat:1.6.1"

            val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

            val core             = "androidx.core:core-ktx:1.10.1"
            val coreSplashScreen = "androidx.core:core-splashscreen:1.0.0-alpha01"

            const val versionLifecycle   = "2.6.1"
            val lifecycleLiveData  = "androidx.lifecycle:lifecycle-livedata-ktx:$versionLifecycle"
            val lifecycleRunTime   = "androidx.lifecycle:lifecycle-runtime-ktx:$versionLifecycle"
            val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$versionLifecycle"

            val multidex = "androidx.multidex:multidex:2.0.1"

            const val versionNavigation  = "2.7.2"
            val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$versionNavigation"
            val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$versionNavigation"
            val navigationUi       = "androidx.navigation:navigation-ui-ktx:$versionNavigation"
        }

        object Google {
            val androidMaterial = "com.google.android.material:material:1.9.0"

            val codeGson = "com.google.code.gson:gson:2.10.1"

            const val versionDaggerHilt = "2.47"
            val daggerHilt         = "com.google.dagger:hilt-android:$versionDaggerHilt"
            val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:$versionDaggerHilt"
        }

        object Javax {
            val javaxInject = "javax.inject:javax.inject:1"
        }

        object JetBrains {
            const val versionCoroutines  = "1.7.3"
            val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$versionCoroutines"
            val coroutinesCore    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionCoroutines"
        }

        object Others {
            val facebookShimmer = "com.facebook.shimmer:shimmer:0.5.0"

            const val versionGlide = "4.15.1"
            val glideCompiler = "com.github.bumptech.glide:compiler:$versionGlide"
            val glideGlide    = "com.github.bumptech.glide:glide:$versionGlide"
        }

        object Squareup {
            const val versionOkhttp = "3.12.12" // Support API 19
            val okhttp = "com.squareup.okhttp3:okhttp:$versionOkhttp"
            val okhttpBom = "com.squareup.okhttp3:okhttp-bom:$versionOkhttp"
            val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$versionOkhttp"

            const val versionRetrofit    = "2.9.0"
            val retrofit = "com.squareup.retrofit2:retrofit:$versionRetrofit"
            val retrofitConverter = "com.squareup.retrofit2:converter-gson:$versionRetrofit"
        }
    }
}

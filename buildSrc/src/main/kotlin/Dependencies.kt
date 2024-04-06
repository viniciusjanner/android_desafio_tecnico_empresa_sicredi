object Dependencies {

    object Version {
        const val versionGradle = "8.1.1"
        const val versionKotlin = "1.8.22"
    }

    object Develop {

        object Androidx {
            val appCompat = "androidx.appcompat:appcompat:1.6.1"

            val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

            val core = "androidx.core:core-ktx:1.12.0"
            val coreSplashScreen = "androidx.core:core-splashscreen:1.0.1"

            const val versionLifecycle = "2.7.0"
            val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$versionLifecycle"
            val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:$versionLifecycle"
            val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$versionLifecycle"

            val multidex = "androidx.multidex:multidex:2.0.1"

            const val versionNavigation = "2.7.7"
            val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$versionNavigation"
            val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$versionNavigation"
            val navigationUi = "androidx.navigation:navigation-ui-ktx:$versionNavigation"

            val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        }

        object Google {
            val androidMaterial = "com.google.android.material:material:1.11.0"

            val codeGson = "com.google.code.gson:gson:2.10.1"

            const val versionDaggerHilt = "2.51.1"
            val daggerHilt = "com.google.dagger:hilt-android:$versionDaggerHilt"
            val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:$versionDaggerHilt"
        }

        object Javax {
            val javaxInject = "javax.inject:javax.inject:1"
        }

        object JetBrains {
            const val versionCoroutines = "1.8.0"
            val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$versionCoroutines"
            val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionCoroutines"
        }

        object Others {
            val facebookShimmer = "com.facebook.shimmer:shimmer:0.5.0"

            const val versionGlide = "4.16.0"
            val glideAnnotations = "com.github.bumptech.glide:annotations:$versionGlide"
            val glideCompiler = "com.github.bumptech.glide:compiler:$versionGlide"
            val glideGlide = "com.github.bumptech.glide:glide:$versionGlide"
            val glideOkhttp = "com.github.bumptech.glide:okhttp3-integration:$versionGlide"
        }

        object Squareup {
            const val versionOkhttp = "4.12.0" // Support API 19
            val okhttp = "com.squareup.okhttp3:okhttp:$versionOkhttp"
            val okhttpBom = "com.squareup.okhttp3:okhttp-bom:$versionOkhttp"
            val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$versionOkhttp"

            const val versionRetrofit = "2.11.0"
            val retrofit = "com.squareup.retrofit2:retrofit:$versionRetrofit"
            val retrofitConverter = "com.squareup.retrofit2:converter-gson:$versionRetrofit"
        }
    }

    object Test {

        object Unit {

            object Androidx {
                const val archCore = "androidx.arch.core:core-testing:2.1.0" // A versão 2.2.0 gera erro de compilação.

                const val testCore = "androidx.test:core:1.5.0"
                const val testExtJunit = "androidx.test.ext:junit:1.1.5" // testes com Coroutines
                const val testExtTruth = "androidx.test.ext:truth:1.5.0"
                const val testRules = "androidx.test:rules:1.5.0"
                const val testRunner = "androidx.test:runner:1.5.2"
            }

            object JetBrains {
                const val versionCoroutines = "1.8.0"
                const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$versionCoroutines"
                const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionCoroutines"
            }

            object Junit {
                const val junit = "junit:junit:4.13.2"
            }

            object Others {
                const val mockito = "org.mockito:mockito-core:5.11.0"
                const val mockk = "io.mockk:mockk:1.13.10"
                const val nhaarmanMockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
            }
        }
    }
}

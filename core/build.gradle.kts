plugins {
    id("java-library")
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Google
    api(Dependencies.Develop.Google.codeGson)

    // Javax
    implementation(Dependencies.Develop.Javax.javaxInject)

    // JetBrains
    api(Dependencies.Develop.JetBrains.coroutinesCore)

    // Squareup
    api(Dependencies.Develop.Squareup.okhttp) { version { strictly(Dependencies.Develop.Squareup.versionOkhttp) } }
    api(platform(Dependencies.Develop.Squareup.okhttpBom))
    api(Dependencies.Develop.Squareup.loggingInterceptor)
    api(Dependencies.Develop.Squareup.retrofit)
    api(Dependencies.Develop.Squareup.retrofitConverter)
}

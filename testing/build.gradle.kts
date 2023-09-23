plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // ----------------------------------------------------------------------------------------------
    // Modules
    // ----------------------------------------------------------------------------------------------
    api(project(":core"))

    // ----------------------------------------------------------------------------------------------
    // Test > Unit
    // ----------------------------------------------------------------------------------------------
    // Androidx
    api(Dependencies.Test.Unit.Androidx.archCore)
    // api(Dependencies.Test.Unit.Androidx.testCore)
    //api(Dependencies.Test.Unit.Androidx.testExtJunit)
    //api(Dependencies.Test.Unit.Androidx.testExtTruth)
    //api(Dependencies.Test.Unit.Androidx.testRules)
    //api(Dependencies.Test.Unit.Androidx.testRunner)

    // JetBrains
    api(Dependencies.Test.Unit.JetBrains.coroutinesTest)

    // Junit
    api(Dependencies.Test.Unit.Junit.junit)

    // Others
    // api(Dependencies.Test.Unit.Others.mockito)
    // api(Dependencies.Test.Unit.Others.mockk)
    api(Dependencies.Test.Unit.Others.nhaarmanMockito)
}

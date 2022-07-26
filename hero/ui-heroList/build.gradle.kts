import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))
    "implementation"(project(Modules.heroInteractors))

    "implementation"(Coil.coil)

    "implementation"(Hilt.android)
    "kapt"(Hilt.compiler)

    "implementation"(SqlDelight.androidDriver)
}
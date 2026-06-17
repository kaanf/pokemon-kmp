import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kmpNativeCoroutines)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    // KMP-NativeCoroutines generates @ObjCName-annotated members.
    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedLogic"
            isStatic = true
            // ViewModels are consumed directly from Swift, so their base class must be
            // exported. The KMP-ObservableViewModel ViewModel is that Swift-facing base
            // (it lets `@StateViewModel`/`@ObservedViewModel` observe our ViewModels).
            // Note: do NOT also export androidx.lifecycle.viewmodel — both modules expose
            // a class named `ViewModel`, and exporting both collides the ObjC names.
            export(libs.kmp.observableviewmodel.core)
        }
    }

    androidLibrary {
       namespace = "com.kaanf.pokemon.sharedLogic"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()

       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.kmp.observableviewmodel.core)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.sqldelight.primitive.adapters)
            implementation(libs.datastore.preferences)

            implementation(libs.koin.core)
            implementation(libs.koin.core.viewmodel)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.turbine)
        }
    }
}

sqldelight {
    databases {
        create("PokemonDatabase") {
            packageName.set("com.kaanf.pokemon.core.database")
        }
    }
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel"))
            .using(module("androidx.lifecycle:lifecycle-viewmodel:${libs.versions.androidx.lifecycle.get()}"))
        substitute(module("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-savedstate"))
            .using(module("androidx.lifecycle:lifecycle-viewmodel-savedstate:${libs.versions.androidx.lifecycle.get()}"))
        substitute(module("org.jetbrains.androidx.lifecycle:lifecycle-runtime"))
            .using(module("androidx.lifecycle:lifecycle-runtime:${libs.versions.androidx.lifecycle.get()}"))
        substitute(module("org.jetbrains.androidx.savedstate:savedstate"))
            .using(module("androidx.savedstate:savedstate:1.4.0"))
    }
}

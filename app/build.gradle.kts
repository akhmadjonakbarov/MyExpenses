import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
    kotlin("kapt")  // Apply Kotlin Kapt plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20" // Replace with your Kotlin version
}

android {
    namespace = "uz.akbarovdev.myexpenses"
    compileSdk = 36

    defaultConfig {
        applicationId = "uz.akbarovdev.myexpenses"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // DI - Koin
    implementation(libs.bundles.koin)




    // Gif
    implementation(libs.coil.gif)
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.json)

    //Refresh by Swipe
    implementation(libs.accompanist.swiperefresh)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)

    // Json Retrofit
    implementation(libs.converter.gson)

    // Database - Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.datastore.preferences)

    // work manager
    implementation(libs.androidx.work.runtime.ktx)

    // JUnit Jupiter
    testImplementation(  libs.junit.jupiter.api) // Or the latest stable version
    testRuntimeOnly(libs.junit.jupiter.engine) // Or the latest stable version
    testImplementation(libs.junit.jupiter.params) // For parameterized tests (optional)
    testImplementation(libs.assertk)
}
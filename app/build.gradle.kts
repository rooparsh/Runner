plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.darklabs.runner"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha02"
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":common-ui"))
    implementation(project(":location"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":navigation"))


    // Dagger Core
    implementation("com.google.dagger:hilt-android:2.41")
    kapt("com.google.dagger:hilt-compiler:2.41")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:1.2.0-alpha03")
    implementation("com.google.android.material:material:1.6.0-alpha02")
    implementation("androidx.compose.material3:material3:1.0.0-alpha05")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-alpha03")


    implementation("androidx.core:core-splashscreen:1.0.0-beta01")
    implementation("androidx.activity:activity-compose:1.5.0-alpha02")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha02")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-alpha03")

    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-alpha03")
}
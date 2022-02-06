plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32

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


    implementation(project(":domain"))
    implementation(project(":navigation"))

    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-compiler:2.40.5")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:1.2.0-alpha02")
    implementation("androidx.compose.material:material:1.2.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-service:2.5.0-alpha01")
    implementation("androidx.activity:activity-compose:1.4.0")

    api("com.google.maps.android:maps-compose:1.0.0")
    api("com.google.android.gms:play-services-maps:18.0.2")
    api("com.google.android.gms:play-services-location:19.0.1")


    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-alpha02")

    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-alpha02")
}
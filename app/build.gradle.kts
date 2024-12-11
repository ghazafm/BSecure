plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt") // Tambahkan ini untuk Hilt
    id("dagger.hilt.android.plugin") // Tambahkan ini untuk Hilt
}

android {
    namespace = "com.mawar.bsecure"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mawar.bsecure"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.play.services.maps)
    implementation(libs.accompanist.permissions) // Use the latest version available
    // Google Play Services Maps
    implementation(libs.play.services.maps.v1802)
    // Firebase Firestore
    implementation(libs.firebase.firestore.ktx) // Replace with the latest version

    // Firebase Realtime Database
    implementation(libs.firebase.database.ktx) // Replace with the latest version
    // Jetpack Compose Maps
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.v230) // Replace with the latest version if needed

    // Google Play Services Location
    implementation(libs.play.services.location)

    implementation(libs.androidx.material) // Use the latest stable version

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10") // Use the latest version available

    // Google Play Services Auth
    implementation("com.google.android.gms:play-services-auth:21.2.0") // Use the latest version available

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(libs.firebase.firestore)
//    implementation(libs.firebase.firestore.ktx)
//    implementation(libs.firebase.database.ktx)

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.44")
    // Uncomment if using annotation processing
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material3:material3:1.1.0-alpha03")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // AndroidX Core and Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.0.0")

    // Google Play Services Maps
    implementation(libs.play.services.maps)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
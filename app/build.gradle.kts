plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

//    detekt
    alias(libs.plugins.detekt)

//    KSP
    alias(libs.plugins.ksp)

//    Hilt
    alias(libs.plugins.hilt)

//    Serialization
    alias(libs.plugins.serialization)

//    Google Services
    alias(libs.plugins.google.services)

//    Secrets Gradle Plugin
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.example.flush"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flush"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEB_CLIENT_ID", "\"${System.getenv("WEB_CLIENT_ID") ?: ""}\"")
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
        buildConfig = true
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

//    Immutable
    implementation(libs.kotlinx.collections.immutable)

//    Material Icon Extended
    implementation(libs.androidx.material.icons.extended)

//    Lottie
    implementation(libs.lottie.compose)

//    Splash Screen
    implementation(libs.androidx.core.splashscreen)

//    detekt
    detektPlugins(libs.detekt.compose)
    detektPlugins(libs.detekt.formatting)

//    Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

//    Serialization
    implementation(libs.kotlinx.serialization.json)

//    Navigation
    implementation(libs.androidx.navigation.compose)

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

//    Coroutines
    implementation(libs.kotlinx.coroutines.play.services)

//    Google Auth
    implementation(libs.play.services.auth)
}

detekt {
    config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
    buildUponDefaultConfig = true

    source.setFrom(files("src/main/java"))

    autoCorrect = true
}

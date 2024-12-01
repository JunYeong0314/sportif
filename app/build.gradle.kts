import java.util.Properties

plugins {
    id("com.google.devtools.ksp")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)
}

// local.properties 파일에 추가한 값 사용
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.jyproject.sportif"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jyproject.sportif"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "NAVER_MAP_CLIENT_ID", localProperties.getProperty("NAVER_MAP_CLIENT_ID"))
        buildConfigField("String", "NAVER_MAP_CLIENT_KEY", localProperties.getProperty("NAVER_MAP_CLIENT_KEY"))
        manifestPlaceholders["NAVER_MAP_CLIENT_ID"] =
            localProperties.getProperty("NAVER_MAP_CLIENT_ID_MF")
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
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.navigation.compose)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.compose.hilt.navigation)
    ksp(libs.hilt.compiler)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.moshi.kotlin)

    // Okhttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    // coroutine
    implementation(libs.kotlin.coroutines.play)

    // Lottie
    implementation(libs.lottie.compose)

    // location
    implementation(libs.play.services.location)

    // Naver Map
    implementation(libs.naver.map)
    implementation(libs.naver.map.location)
}
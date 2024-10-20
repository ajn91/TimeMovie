plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.example.mvvmtest"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.mvvmtest"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
        buildConfigField ("String","API_KEY","f60ae473c8d03fdd88e75848cea96a8e")
        buildConfigField ("String","BASE_URL","https://api.themoviedb.org/3/")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()

        // Enable Coroutines and Flow APIs
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
    buildFeatures {
        compose = true
        buildConfig = true

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)







    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.glide)
    implementation(libs.androidx.material3)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.taskshmanager"
    compileSdk = 36 // 👈 Lo dejamos en 36 estable y estándar

    defaultConfig {
        applicationId = "com.example.taskshmanager"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // BOM administra automáticamente las versiones compatibles entre sí
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidthings)

    // Core & Compose Basicos
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material 3 & Íconos
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended") // Para íconos como School, CollectionsBookmark, etc.

    // Navegación en Compose (sin el generator antiguo que rompía el XML)
    implementation(libs.androidx.navigation.compose)

    // Tests & Debug
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

// Bloque de seguridad por si alguna otra librería intenta meter xmlpull duplicado
configurations.all {
    exclude(group = "xmlpull", module = "xmlpull")
    exclude(group = "xpp3", module = "xpp3")
}
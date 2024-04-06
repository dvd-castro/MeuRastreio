import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.jetbrainsKotlinAndroid.get().pluginId)
    id(libs.plugins.googleServices.get().pluginId)
    id(libs.plugins.firebaseCrashlytics.get().pluginId)
    id(libs.plugins.firebasePerf.get().pluginId)
}

val localPropertiesFile = rootProject.file("local.properties")

val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

android {

    namespace = "br.com.davidcastro.meurastreio"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("keystore.jks")
            storePassword = localProperties.getProperty("PASSWORD")
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = "br.com.davidcastro.meurastreio"
        minSdk = 23
        targetSdk = 34
        versionCode = 46
        versionName = "3.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", localProperties["BASE_URL"].toString())
        resValue("string", "google_ads_key", localProperties["GOOGLE_ADS_KEY"].toString())
    }

    buildTypes {
        debug {
            isDebuggable = true
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "google_ads_banner1", localProperties["GOOGLE_ADS_BANNER_TEST"].toString())
            resValue("string", "google_ads_banner2", localProperties["GOOGLE_ADS_BANNER_TEST"].toString())
            resValue("string", "google_ads_banner3", localProperties["GOOGLE_ADS_BANNER_TEST"].toString())
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "google_ads_banner1", localProperties["GOOGLE_ADS_BANNER_1"].toString())
            resValue("string", "google_ads_banner2", localProperties["GOOGLE_ADS_BANNER_2"].toString())
            resValue("string", "google_ads_banner3", localProperties["GOOGLE_ADS_BANNER_3"].toString())
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.bom.analytics)
    implementation(libs.firebase.bom.crashlytics)
    implementation(libs.firebase.bom.perf)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.test)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.play.services.ads)
    implementation(libs.androidx.core.splashscreen)

    implementation(project(":features:inserttracking"))
    implementation(project(":features:trackingdetails"))
    implementation(project(":features:home"))
    implementation(project(":ui"))
    implementation(project(":data"))
}
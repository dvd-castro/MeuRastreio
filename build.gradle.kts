// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.perf.plugin)
    }
}
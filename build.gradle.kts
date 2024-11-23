// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.jetbrains.kotlin.serialization)
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.room) apply false
  alias(libs.plugins.compose) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.spotless)
}
spotless {
  kotlin {
    target("**/*.kt", "**/*.kts")
    targetExclude("${layout.buildDirectory}/**/*.kt", "bin/**/*.kt", "buildSrc/**/*.kt")

    ktlint()
  }
}

val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin(libs.plugins.kotlin.get().pluginId).version(libs.versions.kotlin.get())

}

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktorClient)
    implementation(libs.bundles.ktorServer)
    implementation(libs.bundles.koin)
    implementation(libs.firebase.admin)
    testImplementation(libs.bundles.testAll)
}

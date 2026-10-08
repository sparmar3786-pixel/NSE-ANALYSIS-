plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.sachin.tradeterminal"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.sachin.tradeterminal"
        minSdk = 23
        targetSdk = 35
        versionCode = 2
        versionName = "1.1.0-phone"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}
dependencies {
    implementation("androidx.webkit:webkit:1.8.0")
    testImplementation("junit:junit:4.13.2")
}

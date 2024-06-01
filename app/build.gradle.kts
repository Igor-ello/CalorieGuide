plugins {
    id("com.android.application")
}

android {
    namespace = "com.obsessed.calorieguide"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.obsessed.calorieguide"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //Material Design
    implementation ("com.google.android.material:material:1.12.0")

    //Room
    implementation ("androidx.room:room-runtime:2.3.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0")

    //Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //OkHttp3
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation ("com.squareup.okhttp3:okhttp:4.7.2")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment:2.6.0")
    implementation ("androidx.navigation:navigation-ui:2.6.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
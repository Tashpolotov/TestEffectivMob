plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.presenter"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core_utils"))

    implementation(project(":profile:data"))
    implementation(project(":profile:domain"))
    implementation(project(":catalog:domain"))


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(mapOf("path" to ":catalog:data")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    androidTestImplementation ("androidx.fragment:fragment-testing:1.6.2")
    //hilt
    implementation(DaggerHilt.android)
    kapt(DaggerHilt.compiler)

    //retrofit
    implementation(Retrofit.retrofit)
    implementation (Retrofit.json)

    //glide
    implementation (Glide.glide)
    implementation (AnnotationProcessor.annotationProcessor)
    val nav_version = "2.7.6"
    //navigation
    implementation (Navigation.navigationFragment)
    implementation (Navigation.navigationKtx)

    //dotsIndic
    implementation (DotsIndicator.dotsindicator)
    implementation (DotsIndicator.circleindicator)

    //vp2
    implementation (Viewpager2.viewpager2)

    //okkhtp
    implementation (Okhttp.interceptor)
    implementation (Okhttp.okhttp)

    //Coroutines
    implementation (Coroutines.coroutines)

    //Viewbindingpropertydelegate
    implementation (Viewbindingpropertydelegate.viewbindingpropertydelegate)

    implementation (LottieAnimations.lottieAnimations)


    implementation ("androidx.room:room-runtime:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")
}
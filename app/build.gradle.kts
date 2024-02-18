plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.monsjoker.namadaexplorer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.monsjoker.namadaexplorer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "AAUXUAMBGPRWLWVFPKSZ_SUPABASE_URL",
                "\"https://aauxuambgprwlwvfpksz.supabase.co/rest/v1/\""
            )
            buildConfigField(
                "String",
                "TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL",
                "\"https://tgwsikrpibxhbmtgrhbo.supabase.co/rest/v1/\""
            )
            buildConfigField("String", "NAMADA_INFO_URL", "\"https://namada.info/\"")
            buildConfigField("String", "IT_NAMADA_RED_URL", "\"https://it.api.namada.red/api/v1/\"")
            buildConfigField(
                "String",
                "NAMADA_RPC_HADESGUARD_TECH_URL",
                "\"https://rpc-namada.staker.space/\""
            )
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "AAUXUAMBGPRWLWVFPKSZ_SUPABASE_URL",
                "https://aauxuambgprwlwvfpksz.supabase.co/rest/v1/"
            )
            buildConfigField(
                "String",
                "TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL",
                "https://tgwsikrpibxhbmtgrhbo.supabase.co/rest/v1/"
            )
            buildConfigField("String", "NAMADA_INFO", "https://namada.info")
            buildConfigField("String", "IT_NAMADA_RED_URL", "\"https://it.api.namada.red/api/v1/\"")
            buildConfigField(
                "String",
                "NAMADA_RPC_HADESGUARD_TECH_URL",
                "\"https://rpc-namada.staker.space/\""
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
        kotlinCompilerVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-common:1.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.material3:material3-android:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.compose.foundation:foundation:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
    implementation("androidx.paging:paging-compose:3.2.0")

    implementation("com.moandjiezana.toml:toml4j:0.7.2")

    implementation("me.onebone:toolbar-compose:2.3.5")
}
kapt {
    correctErrorTypes = true
}
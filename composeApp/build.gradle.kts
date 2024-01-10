import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val compose_version = "1.3.0"
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.21"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting
        val ktorVersion = "2.3.2"
        
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            //gson serrialization
            implementation("com.google.code.gson:gson:2.10.1")
        }
        commonMain.dependencies {

            implementation(projects.shared)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            //gson serrialization
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9") //для многопоточности на андроид
    }
}
dependencies {


//    implementation("io.ktor:ktor-client:2.3.2")
//    implementation("io.ktor:ktor-client-cio:2.3.2")


    implementation("androidx.navigation:navigation-compose:2.6.0-alpha07")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9") //для многопоточности
//    implementation("io.ktor:ktor:2.3.2")
//    implementation("io.ktor:ktor-client:2.3.2")
//    implementation("io.ktor:ktor-client-cio:2.3.2") //web engine
//    implementation("io.ktor:ktor-client-core:2.3.2") //web engine commonMain
//    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
//    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.5.0-dev1049")
//    implementation("com.mikepenz:materialdrawer-nav:9.0.1")
//    implementation("com.auth0:java-jwt:4.3.0")
//    implementation("de.sfuhrm:message-digest-assembly-handler:0.9.3")
//    implementation("org.jetbrains.exposed:exposed:0.17.14")
//    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
//    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
//    implementation("org.ktorm:ktorm-core:2.3.2")
//    implementation("org.ktorm:ktorm-support-postgresql:2.3.2")
//
//    //decompose library
//    implementation("com.arkivanov.decompose:decompose:2.0.0-compose-experimental")
//
//    //library insetsx
//    implementation ("com.github.florent37.insetsx:insetsx-compose:0.27.0")
//
//
//
//    //dependencies Exposed and PostgreSQL
//    implementation("org.jetbrains.exposed:exposed-core:0.35.1")
//    implementation("org.jetbrains.exposed:exposed-dao:0.35.1")
//    implementation("org.jetbrains.exposed:exposed-jdbc:0.35.1")
//    implementation("org.postgresql:postgresql:42.3.1")


}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
    dependencies {
        implementation(libs.compose.ui)
        implementation(libs.compose.ui.tooling.preview)
        implementation(libs.compose.material)
//        implementation("org.postgresql:postgresql:42.3.1")
//        implementation("io.ktor:ktor-client:2.3.2")
//        implementation("io.ktor:ktor-client-cio:2.3.2") //web engine

    }
}

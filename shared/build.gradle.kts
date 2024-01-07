plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation("io.ktor:ktor-client-core:2.3.2")
            implementation("io.ktor:ktor-client-cio:2.3.2")

            //gson serrialization
            implementation("io.ktor:ktor-client-gson:2.3.2")
        }
    }
}

android {
    namespace = "org.example.project.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
dependencies {
//    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.5.0-dev1049")
//    implementation("de.sfuhrm:message-digest-assembly-handler:0.9.3")
//    implementation("com.auth0:auth0:2.0.0")
//    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
//    implementation("org.jetbrains.exposed:exposed:0.17.14")
//    implementation("org.jetbrains.exposed:exposed-core:0.41.1") //exposed-core and exposed-jdbc diff version
//    implementation("org.jetbrains.exposed:spring-transaction:0.41.1")
//    implementation("org.jetbrains.exposed:spring-transaction:0.41.1")

//    implementation("io.ktor:ktor-io-jvm:2.3.6")
//    implementation("io.ktor:ktor-client-jvm:2.3.6")
//    implementation("io.ktor:ktor-client-cio-jvm:2.3.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //logging
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:slf4j-simple:2.0.7")

    //dependencies Ktor client
    implementation("io.ktor:ktor-client-json:1.6.8")
    implementation("io.ktor:ktor-client-features:1.6.8")
    implementation("io.ktor:ktor-client-cio:2.3.2")
    implementation("io.ktor:ktor-network-tls-certificates:2.3.2")

    //dependencies Exposed and PostgreSQL
    implementation("org.jetbrains.exposed:exposed-core:0.35.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.35.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.postgresql:postgresql:42.2.23")


//    implementation("com.arkivanov.decompose:decompose:2.0.0-compose-experimental")
//    implementation(project(":shared"))

    testImplementation(kotlin("test"))

//    // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
//    runtimeOnly("io.ktor:ktor-client-core-jvm:2.3.6")
//    // https://mvnrepository.com/artifact/io.ktor/ktor-client-cio
//    runtimeOnly("io.ktor:ktor-client-cio-jvm:2.3.6")
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

var PROJECT_VERSION: String = "1.1"
var EXPOSED: String = "0.38.1"
var KTOR: String = "2.0.1"
var VK_API_SDK: String = "1.0.14"
var LOGBACK: String = "1.2.11"
var REFLECT: String = "1.6.21"
var LOGGING: String = "2.1.21"
var JVM_TARGET: String = "1.8"
var DOTENV: String = "6.2.2"
var GSON: String = "2.9.0"

plugins {
    application
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.4.21"
}

application {
    mainClass.set("ser.MainKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

group = "ser"
version = PROJECT_VERSION

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:${KTOR}")
    implementation("io.ktor:ktor-server-netty:${KTOR}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${KTOR}")
    implementation("io.ktor:ktor-server-content-negotiation:${KTOR}")
    implementation("io.ktor:ktor-client-core:${KTOR}")
    implementation("io.ktor:ktor-client-cio:${KTOR}")
    implementation("org.jetbrains.exposed", "exposed-core", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.1")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("ch.qos.logback:logback-classic:${LOGBACK}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${REFLECT}")
    implementation("io.github.microutils:kotlin-logging-jvm:${LOGGING}")
    implementation(kotlin("script-runtime"))
    implementation("com.vk.api:sdk:${VK_API_SDK}")
    implementation("io.github.cdimascio:dotenv-kotlin:${DOTENV}")
    implementation("com.google.code.gson:gson:${GSON}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JVM_TARGET
}

tasks {
    create("stage").dependsOn("installDist")
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-javafx
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-debug
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.6.4")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
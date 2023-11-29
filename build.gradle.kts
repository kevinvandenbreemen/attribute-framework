plugins {
    kotlin("jvm") version "1.8.21"
}

group = "com.vandenbreemen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {

    val kluent_version = "1.73"

    val sqlite_dao_version = "1.1.2.0002"
    implementation("com.github.kevinvandenbreemen:sqlite-dao:$sqlite_dao_version")

    testImplementation(kotlin("test"))
    testImplementation("org.amshove.kluent:kluent:$kluent_version")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
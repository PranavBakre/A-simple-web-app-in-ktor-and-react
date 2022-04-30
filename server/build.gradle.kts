val ktor_version: String by project
val kotlin_version: String by project
val log4j2_version: String by project
val exposedVersion: String by project
plugins {
    application
    kotlin("jvm") version "1.6.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.20"
}

repositories {
    mavenCentral()
}

group = "in.psbakre.ktor"
version = "0.0.1"
application {
    mainClass.set("in.psbakre.ktor.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.register<JavaExec>("configureDatabase") {
    dependsOn("build")
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("in.psbakre.ktor.DatabaseKt")
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-id-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    //Dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    //Ktor Client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:$log4j2_version")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j18-impl
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:$log4j2_version")

    // https://mvnrepository.com/artifact/commons-validator/commons-validator
    implementation("commons-validator:commons-validator:1.7")
    // https://mvnrepository.com/artifact/com.github.wvengen/proguard-maven-plugin
    implementation("com.github.wvengen:proguard-maven-plugin:2.5.3")

    // Jetbrains Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.28")

    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")

    //Auth0 Java JWT
    implementation("com.auth0:java-jwt:3.19.1")




    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}
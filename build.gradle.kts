val kotlinVersion = "2.1.10"
val logbackVersion= "1.5.6"
val logstashEncoderVersion = "8.0"
val poiVersion = "5.3.0"
val sendgridVersion = "4.10.3"
val junitJupiterVersion = "5.11.4"


plugins {
    id("application")
    kotlin("jvm") version "2.1.10"
}

group = "mikaojk.github.io"
version = "0.0.1"

application {
    mainClass.set("mikaojk.github.io.ApplicationKt")
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.poi:poi-ooxml:$poiVersion")
    implementation("com.sendgrid:sendgrid-java:$sendgridVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks {

    withType<Test> {
        useJUnitPlatform {}
        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }



}

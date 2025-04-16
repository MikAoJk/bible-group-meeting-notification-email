val javaVersion = 21

val kotlinVersion = "2.1.10"
val logbackVersion= "1.5.18"
val logstashEncoderVersion = "8.0"
val poiVersion = "5.4.1"
val sendgridVersion = "4.10.3"
val junitJupiterVersion = "5.12.2"


plugins {
    id("application")
    kotlin("jvm") version "2.1.20"
}

group = "mikaojk.github.io"
version = "0.0.1"

application {
    mainClass.set("mikaojk.github.io.ApplicationKt")
}

kotlin {
    jvmToolchain(javaVersion)
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
            events("passed", "skipped", "failed")
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }

}

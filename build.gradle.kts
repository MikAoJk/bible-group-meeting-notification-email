val kotlinVersion = "2.0.20"
val logbackVersion= "1.5.6"
val logstashEncoderVersion = "8.0"
val poiVersion = "5.3.0"
val sendgridVersion = "4.10.2"

plugins {
    id("application")
    kotlin("jvm") version "2.0.20"
}

group = "mikaojk.github.io"
version = "0.0.1"

application {
    mainClass.set("mikaojk.github.io.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.poi:poi-ooxml:$poiVersion")
    implementation("com.sendgrid:sendgrid-java:$sendgridVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
}
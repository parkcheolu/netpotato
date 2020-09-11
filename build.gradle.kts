plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.51.Final")
    implementation(platform("io.projectreactor:reactor-bom:Dysprosium-SR11"))
    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor.kafka:reactor-kafka")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.3.RELEASE"))
    implementation("org.springframework.boot:spring-boot-starter") {
//        exclude("ch.org.logback:logback-classic")
    }

    implementation("org.apache.kafka:kafka-clients:2.6.0")
    implementation("org.slf4j:slf4j-api:1.7.30")
//    implementation("org.slf4j:slf4j-simple:1.7.30")

    testImplementation("junit:junit:4.12")
}

tasks {
    test {
        exclude("**/*")
    }
}
plugins {
    java
    id("org.springframework.boot") version "2.3.3.RELEASE"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_13
    targetCompatibility = JavaVersion.VERSION_13
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.51.Final")
    implementation(platform("io.projectreactor:reactor-bom:Dysprosium-SR11"))
    implementation("io.projectreactor:reactor-core")
//    implementation("io.projectreactor.kafka:reactor-kafka")

    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.3.RELEASE"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.kafka:kafka-streams:2.6.0")
    implementation("org.apache.kafka:kafka-clients:2.6.0")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testImplementation("junit:junit:4.12")
}

tasks {
    test {
        exclude("**/*")
    }

    jar {
        archiveBaseName.set("${project.name}-all");

        manifest {
            attributes["Main-Class"] = "io.github.parkcheolu.netpotato.NetpotatoApplication"
        }

        from(configurations["runtimeClasspath"].map({file -> project.zipTree(file) }))
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClassName = "io.github.parkcheolu.netpotato.NetpotatoApplication"
    archiveBaseName.set("${project.name}-all");
}

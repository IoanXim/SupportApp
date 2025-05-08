plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.24"
    java
}

group = "supportapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17 //

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starter Web for REST APIs
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Thymeleaf
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Spring Data JPA for repository layer
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL driver
    runtimeOnly("com.mysql:mysql-connector-j")

    // Lombok for annotations
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // For dev/test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

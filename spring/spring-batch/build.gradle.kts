plugins {
    application
    id("org.springframework.boot").version("3.5.7")
    id("io.spring.dependency-management").version("1.1.7")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.12.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.h2database:h2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "hansanhha.SpringBatchApplication"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

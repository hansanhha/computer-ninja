plugins {
    application
    id("org.springframework.boot").version("3.5.7")
    id("io.spring.dependency-management").version("1.1.7")
}

repositories {
    mavenCentral()
}

configurations.compileOnly.get().extendsFrom(configurations.annotationProcessor.get())

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    
    implementation(platform("org.springframework.modulith:spring-modulith-bom:1.4.4"))
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")

    runtimeOnly("com.h2database:h2:2.4.240")
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    runtimeOnly("org.springframework.modulith:spring-modulith-starter-insight")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "modulith.SpringModulithApplication"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

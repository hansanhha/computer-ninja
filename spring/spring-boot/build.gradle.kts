plugins {
    application
    id("org.springframework.boot").version("3.5.5")
    id("io.spring.dependency-management").version("1.1.7")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

plugins.withType<JavaPlugin> {
    the<JavaPluginExtension>().toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
    }
}
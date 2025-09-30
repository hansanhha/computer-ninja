plugins {
    application
    id("org.springframework.boot").version("3.5.5")
    id("io.spring.dependency-management").version("1.1.7")
}

repositories {
    mavenCentral()
}

dependencies {
    testAnnotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api") // java 17 ~
    annotationProcessor("jakarta.persistence:jakarta.persistence-api") // java 17 ~
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta") // java 17 ~

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql:42.7.8")
}

configurations.compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
}

configurations.testCompileOnly {
    extendsFrom(configurations.testAnnotationProcessor.get())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass = "hansanhha.SpringDataJpaApplication"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("STARTED", "PASSED", "FAILED", "SKIPPED")
    }
}

val queyrdslDir = "src/main/querydsl"
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(queyrdslDir))
}

tasks.withType<Delete> {
    delete(queyrdslDir)
}
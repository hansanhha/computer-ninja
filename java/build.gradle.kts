plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")

    testImplementation("org.assertj:assertj-core:4.0.0-M1")

    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.12.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

tasks.withType<JavaCompile> {
    options.release.set(25)
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test> {
    jvmArgs("--enable-preview")
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}
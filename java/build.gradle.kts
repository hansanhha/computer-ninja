subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"("org.junit.platform:junit-platform-launcher:")
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.12.2")
        "testImplementation"("org.junit.jupiter:junit-jupiter-engine:5.12.2")
        "testImplementation"("org.junit.jupiter:junit-jupiter-params:5.12.2")
        "testImplementation"("org.assertj:assertj-core:4.0.0-M1")
    }

    plugins.withType<JavaPlugin> {
        the<JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(24))
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(24)
        options.compilerArgs.add("--enable-preview")
    }

    tasks.withType<Test> {
        jvmArgs("--enable-preview")
        useJUnitPlatform()
    }

    tasks.withType<JavaExec> {
        jvmArgs("--enable-preview")
    }
}
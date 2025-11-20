// 루트 프로젝트 빌드 로직
plugins {
    java
    id("org.springframework.boot") version "3.5.7" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

// 각 모듈에 대한 공통 설정
subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java")

    // 조건문을 사용하여 특정 모듈을 제외할 수 있다
    if (name != "common") {
        apply(plugin = "io.spring.dependency-management")
        apply(plugin = "org.springframework.boot")
        apply(plugin = "jacoco")

        dependencies {
            implementation("org.springframework.boot:spring-boot-starter-web")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }

        if (name != "app") {
            configurations.compileOnly.get().extendsFrom(configurations.annotationProcessor.get())

            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-validation")
                implementation("org.springframework.boot:spring-boot-starter-data-jpa")
                implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
                annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
                annotationProcessor("jakarta.annotation:jakarta.annotation-api")
                annotationProcessor("jakarta.persistence:jakarta.persistence-api")   

                runtimeOnly("com.h2database:h2")
            }

            // app이 아닌 모든 하위 모듈은 명시적으로 bootJar, bootRun을 비활성화하고 jar 태스크만 활성화한다
            // app의 bootRun과 bootJar는 스프링 부트 그레이들 플러그인에 의해 자동적으로 활성화된다
            tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
                enabled = false
            }
            tasks.named< org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
                enabled = false
            }
            tasks.named<Jar>("jar") {
                enabled = true
            }
        }
    }

    // 하위 모듈의 Test 태스크 공통 설정
    tasks.named<Test>("test") {
       useJUnitPlatform()
    }

    // 하위 모듈의 Java 플러그인 공통 설정
    plugins.withType<JavaPlugin> {
        the<JavaPluginExtension>().apply {
            toolchain {
                languageVersion = JavaLanguageVersion.of(25)
            }
        }
    } 
}
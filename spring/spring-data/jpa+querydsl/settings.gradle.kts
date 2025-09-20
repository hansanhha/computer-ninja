rootProject.name = "spring-data-jpa+querydsl"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(
    "example-project",
    "performance",
    "concurrency",
    "transaction",
    "entity",
)
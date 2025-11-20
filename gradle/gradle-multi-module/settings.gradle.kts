// settings.gradle.kts
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "gradle-multi-module"

// app, common, order, payment, product, user 모듈을 포함한다
include(
    "app",
    "common",
    "inventory",
    "order",
    "payment",
    "product",
    "user",
)
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

include(
    "lang",
    "functional",
    "collections",
    "utils",
    "network",
    "io",
    "security",
    "thread",
    "jvm",
    "tools",
    "native",
    "foreign",
    "experimental",
)
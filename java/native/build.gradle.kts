plugins {
    id("org.graalvm.buildtools.native").version("0.10.6")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
        vendor.set(JvmVendorSpec.GRAAL_VM)
    }
}
plugins {
    application
}

dependencies {
    implementation(project(":common"))
    implementation(project(":order"))
    implementation(project(":payment"))
    implementation(project(":product"))
    implementation(project(":user"))
}

application {
    mainClass = "multi_module.GradleMultiModuleApplication"
}
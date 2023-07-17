dependencies {
    implementation(project(":common-api"))
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
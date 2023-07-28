dependencies {
    implementation(project(":common-api"))
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
dependencies {
    api(project(":common-api"))
    implementation("net.mamoe:mirai-core:2.15.0-RC")
    implementation("net.mamoe:mirai-core-mock:2.13.0")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
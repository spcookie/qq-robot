dependencies {
    implementation(project(":common-api"))
    implementation(fileTree("libs") { include("*.jar") })
    implementation("org.asynchttpclient:async-http-client:2.12.3")
    implementation("net.mamoe:mirai-core:2.15.0")
    implementation("net.mamoe:mirai-core-mock:2.13.0")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
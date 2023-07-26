dependencies {
    implementation(project(":common-api"))
    api("com.alibaba.csp:sentinel-apache-dubbo3-adapter:1.8.6")
    api("com.alibaba.csp:sentinel-transport-simple-http:1.8.6")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
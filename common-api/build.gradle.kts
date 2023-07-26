dependencies {
    api("com.google.protobuf:protobuf-java:3.23.3")
    api("org.apache.dubbo:dubbo-spring-boot-starter:3.2.4")
    implementation("org.apache.dubbo:dubbo-dependencies-zookeeper:3.2.4")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
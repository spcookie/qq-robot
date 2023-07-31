dependencies {
    api("com.google.protobuf:protobuf-java:3.23.3")
    api("org.apache.dubbo:dubbo-spring-boot-starter:3.2.4")
    api("org.apache.dubbo:dubbo-dependencies-zookeeper:3.2.4")
    api("org.springframework.boot:spring-boot-starter-validation")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
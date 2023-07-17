dependencies {
    api("com.google.protobuf:protobuf-java:3.23.3")
    api("org.apache.dubbo:dubbo-spring-boot-starter:3.2.4")
    api("com.alibaba.nacos:nacos-client:2.2.1")
    api("com.alibaba.csp:sentinel-apache-dubbo3-adapter:1.8.6")
    api("com.alibaba.csp:sentinel-transport-simple-http:1.8.6")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
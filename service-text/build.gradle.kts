dependencies {
    implementation(project(":common-api"))
//    compileOnly("org.projectlombok:lombok")
//    annotationProcessor("org.projectlombok:lombok")
//    implementation("com.theokanning.openai-gpt3-java:api:0.14.0")
    implementation("org.springframework.retry:spring-retry")
    implementation("com.alibaba.csp:sentinel-core:1.8.6")
    implementation("com.alibaba.csp:sentinel-annotation-aspectj:1.8.6")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
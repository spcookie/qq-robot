dependencies {
    implementation(project(":common-api"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.retry:spring-retry")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
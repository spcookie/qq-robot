plugins {
    kotlin("plugin.jpa") version "1.8.22"
}

dependencies {
    implementation(project(":common-api"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.retry:spring-retry")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.minio:minio:8.5.4")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.alibaba.csp:sentinel-core:1.8.6")
    implementation("com.alibaba.csp:sentinel-annotation-aspectj:1.8.6")
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
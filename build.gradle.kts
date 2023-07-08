import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.13"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
}

repositories {
    maven("https://repo.mirai.mamoe.net/snapshots")
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    maven("https://maven.aliyun.com/repository/spring")
    maven("https://maven.aliyun.com/repository/spring-plugin")
    maven("https://maven.aliyun.com/mvn/guide")
    maven("https://maven.aliyun.com/repository/apache-snapshots")
    mavenCentral()
}

subprojects {
    apply{
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    group = "io.net"
    version = "3.0.0"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        maven("https://repo.mirai.mamoe.net/snapshots")
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/spring")
        maven("https://maven.aliyun.com/repository/spring-plugin")
        maven("https://maven.aliyun.com/mvn/guide")
        maven("https://maven.aliyun.com/repository/apache-snapshots")
        mavenCentral()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.apache.dubbo:dubbo-spring-boot-starter")
        implementation("com.alibaba.nacos:nacos-client")
        implementation("com.alibaba.csp:sentinel-apache-dubbo3-adapter:1.8.6")
        implementation("com.alibaba.csp:sentinel-transport-simple-http:1.8.6")
        implementation("com.google.protobuf:protobuf-java")
//        implementation("io.grpc:grpc-stub")
//        implementation("io.grpc:grpc-protobuf")
//        implementation("io.grpc:grpc-netty")
//        implementation("io.grpc:grpc-netty-shaded")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom("io.grpc:grpc-bom:1.34.1")
            mavenBom("org.apache.dubbo:dubbo-bom:3.2.0")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

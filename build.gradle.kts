import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.bmuschko.docker-remote-api") version "6.7.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

repositories {
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
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("com.bmuschko.docker-remote-api")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    group = "io.net"
    version = "3.0.0"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
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
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
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

    extra["dockerRegistry"] = "local"

    docker {
        url.set("npipe:////./pipe/docker_engine")

        registryCredentials {
            url.set("tcp://${extra["dockerRegistry"]}")
            username.set("supercookies")
            password.set("@lwx130YI")
        }
    }

    tasks.register<Copy>("copyJar") {
        dependsOn.add(tasks.bootJar.get())
        from("$buildDir/libs")
        into("$projectDir/docker")
    }

    tasks.register("buildDockerImage", DockerBuildImage::class) {
        dependsOn.add(tasks.named<Copy>("copyJar"))
        inputDir.set(file("docker"))
        pull.set(true)
        images.add("${extra["dockerRegistry"]}/$name:$version")
    }
}
plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example.service"
version = "0.0.1-SNAPSHOT"

//java {
//	sourceCompatibility = JavaVersion.VERSION_21
//}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("io.lettuce:lettuce-core")

	implementation("org.springframework.boot:spring-boot-starter-cache:3.2.4")
	implementation("com.google.guava:guava:32.1.1-jre")

	runtimeOnly("org.postgresql:postgresql")

	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	implementation("com.squareup.okhttp3:okhttp:4.12.0")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6")
	testImplementation("org.testcontainers:junit-jupiter:1.17.6")
	testImplementation("org.testcontainers:postgresql:1.17.6")
	testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.0")
	testImplementation("net.javacrumbs.json-unit:json-unit:2.38.0")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

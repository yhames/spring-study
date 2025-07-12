val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.redisson:redisson-spring-boot-starter:3.50.0")	// redisson는 lettuce와 다르게 분산락 인터페이스가 있고, 스핀락이 아닌 pub/sub 방식으로 동작합니다.
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

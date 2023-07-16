plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("kapt") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id("signing")
    id("maven-publish")
}

group = "io.github.yuitosato"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.7.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    kapt("com.google.auto.service:auto-service:1.1.1")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val jvmVersion = 17

kotlin {
    jvmToolchain {
        (this).languageVersion.set(JavaLanguageVersion.of(jvmVersion))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "kotlin-no-data-class-copy-plugin"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("kotlin-no-data-class-copy-plugin")
                description.set("A Kotlin compiler plugin that disallows the usage of copy method for data classes with private constructors.")
                url.set("https://github.com/YuitoSato/kotlin-no-data-class-copy-plugin")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("YuitoSato")
                        name.set("Yuito Sato")
                        email.set("yuitosato.w@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:YuitoSato/kotlin-no-data-class-copy-plugin.git")
                    developerConnection.set("git@github.com:YuitoSato/kotlin-no-data-class-copy-plugin.git")
                    url.set("https://github.com/YuitoSato/kotlin-no-data-class-copy-plugin")
                }
            }
        }
    }
    repositories {
        maven {
            credentials {
                val sonatypeUsername: String? by project
                val sonatypePassword: String? by project
                username = sonatypeUsername
                password = sonatypePassword
            }

            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

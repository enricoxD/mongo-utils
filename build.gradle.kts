plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    `maven-publish`
}

group = "de.hglabor"
version = "1.0.15-SNAPSHOT"


val nexusUsername = (System.getenv("NORISK_NEXUS_USERNAME") ?: project.findProperty("noriskMavenUsername") ?: "").toString()
val nexusPassword = (System.getenv("NORISK_NEXUS_PASSWORD") ?: project.findProperty("noriskMavenPassword") ?: "").toString()

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://maven.norisk.gg/repository/maven-snapshots/")
        credentials {
            username = nexusUsername
            password = nexusPassword
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC.2")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.4")
}

val sourceJar = tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "de.hglabor"
            artifactId = "mongoutils"
            version = project.version.toString()
            from(components["java"])
            artifact(sourceJar)
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://maven.norisk.gg/repository/maven-snapshots/")
            val nexusUsername = System.getenv("NORISK_NEXUS_USERNAME") ?: project.findProperty("noriskMavenUsername") ?: ""
            val nexusPassword = System.getenv("NORISK_NEXUS_PASSWORD") ?: project.findProperty("noriskMavenPassword") ?: ""

            name = "nexus"
            url = releasesRepoUrl
            credentials {
                username = nexusUsername.toString()
                password = nexusPassword.toString()
            }
        }
    }
}

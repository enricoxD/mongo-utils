plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.22"
    `maven-publish`
}

group = "de.hglabor"
version = "1.0.8-SNAPSHOT"


val nexusUsername = (System.getenv("NORISK_NEXUS_USERNAME") ?: project.findProperty("noriskMavenUsername") ?: "").toString()
val nexusPassword = (System.getenv("NORISK_NEXUS_PASSWORD") ?: project.findProperty("noriskMavenPassword") ?: "").toString()

repositories {
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
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.11.0")
    implementation("org.litote.kmongo", "kmongo-serialization-mapping", "4.11.0")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("ch.qos.logback:logback-classic:1.4.14")
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
            val releasesRepoUrl = uri("https://maven.norisk.gg/repository/maven-releases/")
            val snapshotsRepoUrl = uri("https://maven.norisk.gg/repository/maven-snapshots/")
            val nexusUsername = System.getenv("NORISK_NEXUS_USERNAME") ?: project.findProperty("noriskMavenUsername") ?: ""
            val nexusPassword = System.getenv("NORISK_NEXUS_PASSWORD") ?: project.findProperty("noriskMavenPassword") ?: ""

            name = "nexus"
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = nexusUsername.toString()
                password = nexusPassword.toString()
            }
        }
    }
}
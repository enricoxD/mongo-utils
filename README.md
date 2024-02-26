# mongo-utils

## Usage

Add this to your build.gradle.kts

```groovy
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
    implementation("de.hglabor:mongoutils:1.0.0-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.litote.kmongo:kmongo-coroutine:4.11.0")
}
```

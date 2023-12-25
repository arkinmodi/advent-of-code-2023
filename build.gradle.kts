plugins {
    application
    java
    id("com.diffplug.spotless") version "6.23.3"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("tools.aqua:z3-turnkey:4.12.2.1")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotless {
    java {
        toggleOffOn()
        importOrder()
        removeUnusedImports()
        googleJavaFormat().aosp().reflowLongStrings()
    }

    kotlinGradle {
        ktlint()
    }
}

val mainClasspath = "com.arkinmodi.adventofcode.Main"

application {
    mainClass = mainClasspath
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = mainClasspath
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

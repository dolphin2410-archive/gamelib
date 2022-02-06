plugins {
    kotlin("jvm") version "1.6.10"
}

group = "me.dolphin2410"
version = "1.0.0"

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
        implementation(kotlin("stdlib"))
        implementation("io.github.monun:invfx-api:3.1.0")
        implementation("io.github.teamcheeze:jaw:1.0.4")
    }
}

tasks {
    register<Jar>("buildJar") {
        from(project(":core").sourceSets["main"].output)
        from(project(":api").sourceSets["main"].output)
        archiveFileName.set("gamelib.jar")
    }

    register<Jar>("buildAddon") {
        from(project(":sample").sourceSets["main"].output)
        archiveFileName.set("sampleAddon.jar")
    }
}
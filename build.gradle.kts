val mainClass = "me.inspect.Main"
val minecraftVersion = "1.21.8"
group = "com.example"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "2.1.20" // Kotlin
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" // PaperAPI
    id("com.gradleup.shadow") version "8.3.7" // ShadowJar
    id("xyz.jpenilla.run-paper") version "2.3.1" // Run Paper Server
    id("de.eldoria.plugin-yml.bukkit") version "0.8.0" // plugin.yml
    id("io.github.revxrsal.bukkitkobjects") version "0.0.4" // JavaPlugin KObject
}

repositories {
    mavenCentral()
    maven("https://repo.flyte.gg/releases")
    maven("https://jitpack.io")
}

dependencies {
    // Paper API
    paperweight.paperDevBundle("${minecraftVersion}-R0.1-SNAPSHOT")

    // Paper/Kotlin Utilities
    implementation("gg.flyte:twilight:1.1.22")

    // Lamp Command Framework
    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.17")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.17")
    implementation("io.github.revxrsal:lamp.brigadier:4.0.0-beta.17")
}

bukkitKObjects {
    classes.add(mainClass)
}

bukkit {
    main = mainClass
    apiVersion = """(\d+\.\d+)""".toRegex().find(minecraftVersion)?.value ?: minecraftVersion
    author = """^[^.]+\.([^.]+)""".toRegex().find(mainClass)?.groupValues?.get(1)
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        javaParameters = true
    }
}

tasks {
    shadowJar {
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
    processResources {
        filteringCharset = "UTF-8"
    }
    runServer {
        minecraftVersion(minecraftVersion)
        downloadPlugins {}
    }
}

// Advanced Hotswapping using Jetbrains JRE (must have the project jar set to Jetbrains jar)
tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}
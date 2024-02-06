plugins {
    `kotlin-dsl`
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:30.1.1-jre")
        force("com.google.code.gson:gson:2.10.1")
    }
}

repositories {
    gradlePluginPortal()
    maven("https://maven.blamejared.com") {
        name = "BlameJared"
    }
}

dependencies {
    gradleApi()
    implementation(group = "com.blamejared", name = "ModTemplate", version = "3.0.0.38")
    implementation(group = "net.darkhax.curseforgegradle", name = "CurseForgeGradle", version = "1.0.10")
}

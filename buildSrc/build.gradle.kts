plugins {
    `groovy-gradle-plugin`
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:30.1.1-jre")
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
    implementation(group = "com.blamejared", name = "ModTemplate", version = "2.0.0.35")
    implementation(group = "net.darkhax.curseforgegradle", name = "CurseForgeGradle", version = "1.0.10")
}

gradlePlugin {
    plugins {
        create("java-conventions") {
            id = "com.blamejared.contenttweaker.java-conventions"
            implementationClass = "com.blamejared.contenttweaker.gradle.JavaConventionsPlugin"
        }
    }
}

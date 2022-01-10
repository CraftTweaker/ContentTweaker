pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge Snapshots"
        }
        maven("https://maven.minecraftforge.net") {
            name = "Forge"
        }
        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "net.minecraftforge.gradle" -> useModule("${requested.id}:ForgeGradle:${requested.version}")
                "org.spongepowered.mixin" -> useModule("org.spongepowered:mixingradle:${requested.version}")
                "com.blamejared.modtemplate" -> useModule("com.blamejared:ModTemplate:${requested.version}")
            }
        }
    }
}

rootProject.name = "ContentTweaker"
include("Common", "Forge", "Fabric")

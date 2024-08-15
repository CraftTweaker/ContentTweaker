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

dependencyResolutionManagement {
    versionCatalogs {
        register("libs") {
            val minecraftVersion = "1.20.1"
            val parchmentTimestamp = "2023.09.03"

            val architecturyApi = version("architecturyApi", "9.1.13")
            val clothConfig = version("clothConfig", "11.1.118")
            val crafttweaker = version("crafttweaker", "14.0.32")
            val crafttweakerAp = version("crafttweakerAp", "3.0.0.15")

            val fabricApi = version("fabricApi", "0.91.0+$minecraftVersion")
            val fabricLoader = version("fabricLoader", "0.15.6")
            val fabricLoom = version("fabricLoom", "1.7-SNAPSHOT")
            val forge = version("forge", "$minecraftVersion-47.1.3")
            val forgeGradle = version("forgeGradle", "6.0.+")
            val librarian = version("librarian", "1.+")
            val minecraft = version("minecraft", minecraftVersion)
            val mixin = version("mixin", "0.8.5")
            val mixinGradle = version("mixinGradle", "0.7-SNAPSHOT")
            val neverWinter = version("neverWinter", "1.0.0")
            val parchmentDecorated = version("parchmentDecorated", "$parchmentTimestamp-$minecraftVersion")
            val parchmentRaw = version("parchmentRaw", parchmentTimestamp)
            val rei = version("rei", "12.0.684")
            val vanillaGradle = version("vanillaGradle", "0.2.1-SNAPSHOT")

            library("architectury.forge", "dev.architectury", "architectury-forge").versionRef(architecturyApi)
            library("cloth.config.forge", "me.shedaniel.cloth", "cloth-config-forge").versionRef(clothConfig)
            library("crafttweaker.common", "com.blamejared.crafttweaker", "CraftTweaker-common-$minecraftVersion").versionRef(crafttweaker)
            library("crafttweaker.fabric", "com.blamejared.crafttweaker", "CraftTweaker-fabric-$minecraftVersion").versionRef(crafttweaker)
            library("crafttweaker.forge", "com.blamejared.crafttweaker", "CraftTweaker-forge-$minecraftVersion").versionRef(crafttweaker)
            library("crafttweaker.ap", "com.blamejared.crafttweaker", "Crafttweaker_Annotation_Processors").versionRef(crafttweakerAp)
            library("fabric.api", "net.fabricmc.fabric-api", "fabric-api").versionRef(fabricApi)
            library("fabric.loader", "net.fabricmc", "fabric-loader").versionRef(fabricLoader)
            library("forge", "net.minecraftforge", "forge").versionRef(forge)
            library("mixin", "org.spongepowered", "mixin").versionRef(mixin)
            library("minecraft.fabric", "com.mojang", "minecraft").versionRef(minecraft)
            library("never.winter", "com.dwarveddonuts.neverwinter", "NeverWinter").versionRef(neverWinter)
            library("parchment.fabric", "org.parchmentmc.data", "parchment-$minecraftVersion").versionRef(parchmentRaw)
            library("parchment.forge", "", "").versionRef(parchmentDecorated)
            library("rei.fabric", "me.shedaniel", "RoughlyEnoughItems-fabric").versionRef(rei)
            library("rei.forge", "me.shedaniel", "RoughlyEnoughItems-forge").versionRef(rei)

            plugin("fabric.loom", "fabric-loom").versionRef(fabricLoom)
            plugin("forgeGradle", "net.minecraftforge.gradle").versionRef(forgeGradle)
            plugin("librarian", "org.parchmentmc.librarian.forgegradle").versionRef(librarian)
            plugin("mixinGradle", "org.spongepowered.mixin").versionRef(mixinGradle)
            plugin("vanillaGradle", "org.spongepowered.gradle.vanilla").versionRef(vanillaGradle)
        }
    }
}

if (file("CraftTweaker-Annotation-Processors").exists()) {
    includeBuild("CraftTweaker-Annotation-Processors") {
        dependencySubstitution {
            substitute(module("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors")).using(project(":"))
        }
    }
}

rootProject.name = "ContentTweaker"
include("core", "vanilla", "forge", "fabric")

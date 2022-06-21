plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-core-$mcVersion")

minecraft {
    version(mcVersion)
}

dependencies {
    val ctVersion = project.extra["ct.version"] as String

    apiCompileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-$mcVersion", version = ctVersion)

    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.4")
    compileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-$mcVersion", version = ctVersion)
}

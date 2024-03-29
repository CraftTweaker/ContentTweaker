plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

evaluationDependsOn(":core")

val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-vanilla-$mcVersion")

minecraft {
    version(mcVersion)
}

dependencies {
    val ctVersion = project.extra["ct.version"] as String

    apiCompileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-$mcVersion", version = ctVersion)
    apiImplementation(project(":core", "apiConfiguration"))

    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.4")
    compileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-$mcVersion", version = ctVersion)
    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
}

// TODO("Remove when ForgeGradle does this itself OR when IntelliJ fixes the -1 bug")
buildscript {
    configurations.classpath.configure {
        resolutionStrategy {
            force(
                "org.apache.logging.log4j:log4j-api:2.11.2",
                "org.apache.logging.log4j:log4j-core:2.11.2"
            )
        }
    }
}

plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
}

val modId = project.extra["mod.id"] as String
val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-forge-$mcVersion")

minecraft {
    mappings(project.extra["minecraft.forge.mappings.channel"] as String, project.extra["minecraft.forge.mappings.version"] as String)

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${modId}.forge.mixins.json")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }
        create("server") {
            workingDirectory(project.file("run_server"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${modId}.forge.mixins.json", "nogui")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")

    config("${modId}.forge.mixins.json")
}

modTemplate {
    modLoader("Forge")
    changelog.enabled(true)
    versionTracker.enabled(true)
    webhook.enabled(true)
}

repositories {
    maven("https://dvs1.progwml6.com/files/maven/") {
        name = "Progwml6"
    }
}

dependencies {
    val ctVersion = project.extra["ct.version"] as String

    minecraft(group = "net.minecraftforge", name = "forge", version = "$mcVersion-${project.extra["minecraft.forge.version"]}")

    implementation(project(":Common"))
    implementation(fg.deobf("com.blamejared.crafttweaker:CraftTweaker-forge-$mcVersion:$ctVersion"))

    runtimeOnly(fg.deobf("mezz.jei:jei-$mcVersion-forge:${project.extra["jei.forge.version"]}"))
}

tasks {
    publishToCurseForge {
        with(upload(project.extra["mod.curse-id"], project.buildDir.resolve("libs/${base.archivesName.get()}-${project.extra["mod.version"]}.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
            addJavaVersion("Java ${project.extra["java.version"]}")
            addGameVersion(mcVersion)
            addRequirement("crafttweaker")
        }
    }
    jar {
        sequenceOf(project, project(":Common")).map { it.sourceSets.main.get().output }.forEach { from(it) }
    }
}

afterEvaluate {
    tasks.jar.get().finalizedBy(tasks.findByName("reobfJar"))
}

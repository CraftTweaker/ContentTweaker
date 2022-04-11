import com.matthewprenger.cursegradle.CurseProject

plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
}

val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-forge-$mcVersion")

curseforge {
    project(closureOf<CurseProject> {
        id = project.extra["mod.curse-id"]
        releaseType = "release"
        changelog = project.file("changelog.md")
        changelogType = "markdown"
        addGameVersion("Forge")
        addGameVersion(mcVersion)
    })
}

minecraft {
    val modId = project.extra["mod.id"] as String

    mappings(project.extra["minecraft.forge.mappings.channel"] as String, project.extra["minecraft.forge.mappings.version"] as String)

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
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
            args("nogui")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }
    }
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
    jar {
        sequenceOf(project, project(":Common")).map { it.sourceSets.main.get().output }.forEach { from(it) }
    }
}

afterEvaluate {
    tasks.jar.get().finalizedBy(tasks.findByName("reobfJar"))
}

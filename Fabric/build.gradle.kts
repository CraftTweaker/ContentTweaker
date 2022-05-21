plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("fabric-loom") version "0.10-SNAPSHOT"
}

val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-fabric-$mcVersion")

loom {
    mixin {
        defaultRefmapName.convention("${extra["mod.id"]}.refmap.json")
    }
    runs {
        named("client") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            programArg("--username=Dev")
        }
        named("server") {
            server()
            ideConfigGenerated(true)
            runDir("run_server")
            programArg("nogui")
        }
    }
}

modTemplate {
    modLoader("Fabric")
    changelog.enabled(true)
    versionTracker.enabled(true)
    webhook.enabled(true)
}

repositories {
    maven("https://maven.shedaniel.me") {
        name = "shedaniel"
    }
}

dependencies {
    val ctVersion = project.extra["ct.version"] as String

    minecraft(group = "com.mojang", name = "minecraft", version = mcVersion)
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$mcVersion:${project.extra["minecraft.fabric.mappings"]}@zip")
    })

    implementation(project(":Common"))
    modImplementation(group = "com.blamejared.crafttweaker", name = "CraftTweaker-fabric-$mcVersion", version = ctVersion) {
        exclude(module = "Crafttweaker_Annotations")
    }
    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = project.extra["fl.version"] as String)
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = project.extra["fapi.version"] as String)

    modRuntimeOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-fabric", version = project.extra["rei.fabric.version"] as String)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    publishToCurseForge {
        with(upload(project.extra["mod.curse-id"], project.buildDir.resolve("libs/${base.archivesName.get()}-${project.extra["mod.version"]}.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
            addJavaVersion("Java ${project.extra["java.version"]}")
            addGameVersion("Fabric")
            addGameVersion(mcVersion)
            addRequirement("crafttweaker")
        }
        dependsOn(project.tasks.remapJar)
    }
    jar {
        from(project(":Common").sourceSets.main.get().output)
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}

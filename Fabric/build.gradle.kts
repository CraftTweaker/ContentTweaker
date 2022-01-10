plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("fabric-loom") version "0.10-SNAPSHOT"
}

val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-fabric-$mcVersion")

curseforge {
    project(closureOf<com.matthewprenger.cursegradle.CurseProject> {
        id = project.extra["mod.curse-id"]
        releaseType = "release"
        changelog = project.file("changelog.md")
        changelogType = "markdown"
        addGameVersion("Fabric")
        addGameVersion(mcVersion)
        mainArtifact(project.buildDir.resolve("libs/${base.archivesName.get()}-${project.extra["mod.version"]}.jar"))
        relations(closureOf<com.matthewprenger.cursegradle.CurseRelation> {
            requiredDependency("crafttweaker")
        })
        afterEvaluate {
            uploadTask.dependsOn(tasks.remapJar)
        }
    })
    options(closureOf<com.matthewprenger.cursegradle.Options> {
        forgeGradleIntegration = false
    })
}

loom {
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
    maven("https://storage.googleapis.com/devan-maven/") {
        name = "Devan"
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
    modImplementation(group = "net.devtech", name = "arrp", version = project.extra["arrp.version"] as String)

    runtimeOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-fabric", version = project.extra["rei.version"] as String)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }

        sequenceOf(project, project(":Common")).map { it.sourceSets.main.get().resources }.forEach { from(it) }

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    jar {
        sequenceOf(project, project(":Common")).map { it.sourceSets.main.get().output }.forEach { from(it) }
    }
}


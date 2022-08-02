plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("fabric-loom") version "0.12-SNAPSHOT"
}

evaluationDependsOn(":vanilla")

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

    apiImplementation(project(":core", "apiConfiguration"))
    apiImplementation(project(":vanilla", "apiConfiguration"))
    // CT is implicitly added because Loom does not support per-source-set deobfuscated dependencies

    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
    implementation(project(":vanilla", "apiConfiguration"))
    implementation(project(":vanilla"))
    modImplementation(group = "com.blamejared.crafttweaker", name = "CraftTweaker-fabric-$mcVersion", version = ctVersion) {
        exclude(module = "Crafttweaker_Annotations")
    }
    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = project.extra["fl.version"] as String)
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = project.extra["fapi.version"] as String)

    modRuntimeOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-fabric", version = project.extra["rei.fabric.version"] as String)
}

tasks {
    compileJava {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.api.get(), it.main.get()) }
            .forEach { source(it.allSource) }
    }
    processResources {
        outputs.upToDateWhen { false }

        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.api.get(), it.main.get()) }
            .forEach { from(it.resources) }

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    publishToCurseForge {
        with(upload(project.extra["mod.curse-id"], project.buildDir.resolve("libs/${base.archivesName.get()}-${project.extra["mod.version"]}.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = when (project.extra["release.fabric.status"]) {
                null, "release" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
                "beta" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA
                "alpha" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_ALPHA
                else -> throw IllegalStateException()
            }
            addJavaVersion("Java ${project.extra["java.version"]}")
            addGameVersion("Fabric")
            addGameVersion(mcVersion)
            addRequirement("crafttweaker")
        }
        dependsOn(project.tasks.remapJar)
    }
    jar {
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}

plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
}

evaluationDependsOn(":vanilla")

val modId = project.extra["mod.id"] as String
val mcVersion = extra["minecraft.version"] as String

base.archivesName.set("${extra["mod.name"]}-forge-$mcVersion")

minecraft {
    mappings(project.extra["minecraft.forge.mappings.channel"] as String, project.extra["minecraft.forge.mappings.version"] as String)

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            sequenceOf("forge", "core", "vanilla").forEach { arg("-mixin.config=${modId}.$it.mixins.json") }
            mods {
                create(modId) {
                    source(sourceSets.api.get())
                    source(sourceSets.main.get())
                    source(project(":core").sourceSets.api.get())
                    source(project(":core").sourceSets.main.get())
                    source(project(":vanilla").sourceSets.api.get())
                    source(project(":vanilla").sourceSets.main.get())
                }
            }
        }
        create("server") {
            workingDirectory(project.file("run_server"))
            ideaModule("${rootProject.name}.${project.name}.main")
            sequenceOf("forge", "core", "vanilla").forEach { arg("-mixin.config=${modId}.$it.mixins.json") }
            arg("nogui")
            mods {
                create(modId) {
                    source(sourceSets.api.get())
                    source(sourceSets.main.get())
                    source(project(":core").sourceSets.api.get())
                    source(project(":core").sourceSets.main.get())
                    source(project(":vanilla").sourceSets.api.get())
                    source(project(":vanilla").sourceSets.main.get())
                }
            }
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")

    sequenceOf("forge", "core", "vanilla").forEach { config("${modId}.$it.mixins.json") }
}

modTemplate {
    modLoader("Forge")
    changelog.enabled(true)
    versionTracker.enabled(true)
}

repositories {
    maven("https://maven.shedaniel.me/") {
        name = "shedaniel"
    }
}

dependencies {
    val ctVersion = project.extra["ct.version"] as String

    minecraft(group = "net.minecraftforge", name = "forge", version = "$mcVersion-${project.extra["minecraft.forge.version"]}")

    annotationProcessor(group = "org.spongepowered", name = "mixin", version = "0.8.5-SNAPSHOT", classifier = "processor")

    apiImplementation(project(":core", "apiConfiguration"))
    apiImplementation(project(":vanilla", "apiConfiguration"))
    apiImplementation(fg.deobf("com.blamejared.crafttweaker:CraftTweaker-forge-$mcVersion:$ctVersion"))

    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
    implementation(project(":vanilla", "apiConfiguration"))
    implementation(project(":vanilla"))
    implementation(fg.deobf("com.blamejared.crafttweaker:CraftTweaker-forge-$mcVersion:$ctVersion"))

    runtimeOnly(fg.deobf("me.shedaniel:RoughlyEnoughItems-forge:${project.extra["rei.forge.version"]}"))
    runtimeOnly(fg.deobf("me.shedaniel.cloth:cloth-config-forge:${project.extra["cloth.forge.version"]}"))
    runtimeOnly(fg.deobf("dev.architectury:architectury-forge:${project.extra["arch.forge.version"]}"))
}

tasks {
    compileJava {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.main.get(), it.api.get()) }
            .forEach{ source(it.allSource) }
    }
    publishToCurseForge {
        with(upload(project.extra["mod.curse-id"], project.buildDir.resolve("libs/${base.archivesName.get()}-${project.extra["mod.version"]}.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = when (project.extra["release.forge.status"]) {
                null, "release" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
                "beta" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA
                "alpha" -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_ALPHA
                else -> throw IllegalStateException()
            }
            addJavaVersion("Java ${project.extra["java.version"]}")
            addGameVersion(mcVersion)
            addRequirement("crafttweaker")
        }
    }
    jar {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.main.get(), it.api.get()) }
            .forEach { from(it.resources) }
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}

afterEvaluate {
    tasks.jar.get().finalizedBy(tasks.findByName("reobfJar"))
}

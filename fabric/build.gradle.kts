import net.darkhax.curseforgegradle.TaskPublishCurseForge
import java.util.Locale

plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    alias(libs.plugins.fabric.loom)
}

evaluationDependsOn(":vanilla")

loom {
    mixin {
        defaultRefmapName.convention("${Constants.MOD_NAME.lowercase(Locale.ENGLISH)}.refmap.json")
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

dependencies {
    minecraft(libs.minecraft.fabric)
    mappings(loom.layered {
        officialMojangMappings()
        parchment("${libs.parchment.fabric.get()}@zip")
    })

    apiImplementation(project(":core", "apiConfiguration"))
    apiImplementation(project(":vanilla", "apiConfiguration"))
    // CT is implicitly added because Loom does not support per-source-set deobfuscated dependencies

    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
    implementation(project(":vanilla", "apiConfiguration"))
    implementation(project(":vanilla"))
    implementation(libs.never.winter)
    include(libs.never.winter)

    modImplementation(libs.crafttweaker.fabric) {
        exclude(module = "CraftTweaker_Annotations")
    }
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.loader)

    modRuntimeOnly(libs.rei.fabric)
}

afterEvaluate {
    configurations.apiImplementation.configure {
        extendsFrom(configurations.modCompileClasspathMapped.get(), configurations.named("minecraftNamedCompile").get())
    }
}

tasks {
    named<JavaCompile>("compileJava") {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.api.get(), it.main.get()) }
            .plusElement(sourceSets.api.get())
            .forEach { source(it.allSource) }
    }
    withType<ProcessResources> {
        outputs.upToDateWhen { false }

        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.api.get(), it.main.get()) }
            .plusElement(sourceSets.api.get())
            .forEach { from(it.resources) }

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    withType<TaskPublishCurseForge> {
        with(upload(Constants.CURSE_ID, project.layout.buildDirectory.file("libs/${base.archivesName.get()}-${version}.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA

            addJavaVersion("Java 17")
            addGameVersion("Fabric")
            addGameVersion(libs.versions.minecraft.get())
            addRequirement("crafttweaker")
            doLast {
                project.ext.set("curse_file_url", "${modTemplate.curseHomepage}/files/${curseFileId}")
            }
        }
        dependsOn(project.tasks.named("remapJar"))
    }
}

import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.minecraftforge.gradle.userdev.tasks.JarJar
import java.util.Locale

plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    alias(libs.plugins.forgeGradle)
    alias(libs.plugins.librarian)
    alias(libs.plugins.mixinGradle)
}

evaluationDependsOn(":vanilla")

minecraft {
    mappings("parchment", libs.versions.parchmentDecorated.get())

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            sequenceOf("forge", "core", "vanilla").forEach {
                arg("-mixin.config=${Constants.MOD_NAME.lowercase(Locale.ENGLISH)}.$it.mixins.json")
            }
            mods {
                create(Constants.MOD_NAME.lowercase(Locale.ENGLISH)) {
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
            sequenceOf("forge", "core", "vanilla").forEach {
                arg("-mixin.config=${Constants.MOD_NAME.lowercase(Locale.ENGLISH)}.$it.mixins.json")
            }
            arg("nogui")
            mods {
                create(Constants.MOD_NAME.lowercase(Locale.ENGLISH)) {
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
    add(sourceSets.main.get(), "${Constants.MOD_NAME.lowercase(Locale.ENGLISH)}.refmap.json")

    sequenceOf("forge", "core", "vanilla").forEach { config("${Constants.MOD_NAME.lowercase(Locale.ENGLISH)}.$it.mixins.json") }
}

modTemplate {
    modLoader("Forge")
    changelog.enabled(true)
    versionTracker.enabled(true)
}

jarJar.enable()

configurations.apiImplementation.configure {
    extendsFrom(configurations.minecraft.get())
}

dependencies {
    minecraft(libs.forge)

    annotationProcessor("${libs.mixin.get()}:processor")

    apiImplementation(project(":core", "apiConfiguration"))
    apiImplementation(project(":vanilla", "apiConfiguration"))
    apiImplementation(fg.deobf(libs.crafttweaker.forge.get()))
    apiImplementation(libs.never.winter)

    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
    implementation(project(":vanilla", "apiConfiguration"))
    implementation(project(":vanilla"))
    implementation(fg.deobf(libs.crafttweaker.forge.get()))

    jarJar.ranged(implementation(libs.never.winter), "[${libs.versions.neverWinter.get()},)")

    runtimeOnly(fg.deobf(libs.architectury.forge.get()))
    runtimeOnly(fg.deobf(libs.cloth.config.forge.get()))
    runtimeOnly(fg.deobf(libs.rei.forge.get()))
}

publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            fg.component(this)
        }
    }
}

reobf {
    register("jarJar") {}
}

tasks {
    named<JavaCompile>("compileJava") {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.main.get(), it.api.get()) }
            .plusElement(sourceSets.api.get())
            .forEach{ source(it.allSource) }
    }
    withType<TaskPublishCurseForge> {
        with(upload(Constants.CURSE_ID, project.layout.buildDirectory.file("libs/${base.archivesName.get()}-${version}-all.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = project.file("changelog.md")
            releaseType = net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA
            addJavaVersion("Java 17")
            addGameVersion(libs.versions.minecraft.get())
            addRequirement("crafttweaker")

            doLast {
                project.ext.set("curse_file_url", "${modTemplate.curseHomepage}/files/${curseFileId}")
            }
        }
    }
    withType<Jar> {
        sequenceOf(project(":core"), project(":vanilla"))
            .map { it.sourceSets }
            .flatMap { sequenceOf(it.main.get(), it.api.get()) }
            .plusElement(sourceSets.api.get())
            .forEach { from(it.resources) }
    }
    withType<JarJar> {
        archiveClassifier.set("all")
    }
}

afterEvaluate {
    tasks.named("jar").configure {
        finalizedBy(tasks.named("reobfJar"))
    }
    tasks.named("jarJar").configure {
        finalizedBy(tasks.named("reobfJarJar"))
    }
}

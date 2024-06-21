import com.blamejared.modtemplate.Utils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import java.nio.charset.StandardCharsets
import java.util.Locale

plugins {
    idea
    java
    `maven-publish`
    id("com.blamejared.modtemplate")
    id("net.darkhax.curseforgegradle")
}

val minecraftVersion = versionCatalogs.named("libs").findVersion("minecraft").get().requiredVersion

val api: SourceSet by java.sourceSets.creating
val apiConfiguration: Configuration by configurations.creating

base.archivesName.set("${Constants.MOD_NAME}-${project.name}-$minecraftVersion")
version = Utils.updatingVersion(Constants.MOD_VERSION)

idea {
    module.excludeDirs.addAll(listOf("run", "run_server", "run_client", "run_data").map(::file))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))

    withSourcesJar()
    withJavadocJar()
}

modTemplate {
    mcVersion(minecraftVersion)
    curseHomepage("https://www.curseforge.com/minecraft/mc-mods/contenttweaker")
    displayName(Constants.MOD_NAME)
    changelog {
        firstCommit("b762862ec40682cfa40f56e730d73b3bf3eb568f") // TODO("We should really look into this")
        repo("https://github.com/CraftTweaker/ContentTweaker")
        changelogFile("changelog.md")
    }
    versionTracker {
        endpoint(System.getenv("versionTrackerAPI"))
        author(Constants.MOD_AUTHORS)
        projectName(Constants.MOD_NAME)
        homepage("https://www.curseforge.com/minecraft/mc-mods/contenttweaker")
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/repository/maven-public/")
    maven("https://maven.parchmentmc.org/")
    maven("https://maven.blamejared.com/")
    maven("https://maven.shedaniel.me/")
    maven("https://gitlab.com/api/v4/projects/40584103/packages/maven/")
}

dependencies {
    implementation(api.output)
    apiConfiguration(api.output)
}

publishing {
    repositories {
        maven("file:///${System.getenv("local_maven")}")
    }
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = "com.blamejared.contenttweaker"
            artifactId = base.archivesName.get().lowercase(Locale.ENGLISH)
            version = project.version.toString()

            // FIXME("Eager resolution is required by MavenPublication's from for some reason")
            from(project.components.getByName("java"))
        }
    }
}

tasks {
    register<TaskPublishCurseForge>("publishToCurseForge") {
        group = "publishing"
        apiToken = System.getenv("curseforgeApiToken")
    }
    withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.name()
        options.release.set(JavaVersion.VERSION_17.majorVersion.toInt())

        outputs.upToDateWhen { false }
    }
    withType<Javadoc> {
        options.encoding = StandardCharsets.UTF_8.name()
    }
    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.FAIL
        manifest {
            attributes(
                "Specification-Title" to Constants.MOD_NAME,
                "Specification-Vendor" to Constants.MOD_AUTHORS,
                "Specification-Version" to archiveVersion.getOrElse("unknown"),
                "Implementation-Title" to "${Constants.MOD_NAME}-${project.name}",
                "Implementation-Vendor" to Constants.MOD_AUTHORS,
                "Implementation-Version" to archiveVersion.getOrElse("unknown"),
                "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                "Built-On-Minecraft" to minecraftVersion
            )
        }
    }
}

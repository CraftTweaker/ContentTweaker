import com.blamejared.modtemplate.Utils
import com.diluv.schoomp.Webhook
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.message.embed.Embed

plugins {
    base
    id("com.blamejared.modtemplate")
}

abstract class PostDiscordUpdateMessageTask @Inject constructor(private val project: Project) : DefaultTask() {
    @TaskAction
    fun run() {
        // Create a new webhook instance for Discord
        val webhook = Webhook(System.getenv("discordCFWebhook"), "${Constants.MOD_NAME} CurseForge Gradle Upload")

        // Craft a message to send to Discord using the webhook.
        val message = Message().apply {
            username = Constants.MOD_NAME
            avatarUrl = "https://i.blamejared.com/cot.png"
            content = "${Constants.MOD_NAME} ${project.version} for Minecraft ${project.libs.versions.minecraft.get()} has been published!"

            val embed = Embed().apply {
                val sources = buildList {
                    fun MutableList<String>.projectSource(project: Project, friendlyName: String, emoji: String) {
                        project.ext.properties["curse_file_url"]?.let { this += "$emoji [$friendlyName]($it)" }
                    }
                    fun MutableList<String>.mavenSource(project: Project) {
                        this += "<:maven:932165250738970634> `${project.group}:${project.base.archivesName.get()}:${project.version}`"
                    }

                    projectSource(project.project(":fabric"), "Fabric", "<:fabric:932163720568782878>")
                    projectSource(project.project(":forge"), "Forge", "<:forge:932163698003443804>")

                    listOf("core", "fabric", "forge", "vanilla").forEach { mavenSource(project.project(":$it")) }
                }.joinToString(separator = "\n")

                if (sources.isNotBlank()) {
                    addField("Download", sources, false)
                }

                addField("Changelog", Utils.getCIChangelog(project, "https://github.com/CraftTweaker/ContentTweaker").take(1000), false)
                color = 0xF16436
            }
            addEmbed(embed)
        }

        webhook.sendMessageUnsafely(message)
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.diluv.schoomp:Schoomp:1.2.6")
    }
}

tasks {
    register<PostDiscordUpdateMessageTask>("postDiscordUpdateMessage") {
        group = "publishing"
    }
}

import com.blamejared.modtemplate.Utils
import com.diluv.schoomp.Webhook
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.message.embed.Embed
import java.io.IOException
import java.util.*

plugins {
    // Need to know about base for posting to CF
    base
    id("com.blamejared.modtemplate")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.diluv.schoomp:Schoomp:1.2.6")
    }
}

val modName = extra["mod.name"] as String
val mcVersion = extra["minecraft.version"] as String
val modRepo = extra["mod.repo"] as String
val modAvatar = extra["mod.avatar"] as String

tasks.create("postDiscord") {

    doLast {
        try {

            // Create a new webhook instance for Discord
            val webhook = Webhook(
                System.getenv("discordCFWebhook"),
                "${modName} CurseForge Gradle Upload"
            )

            // Craft a message to send to Discord using the webhook.
            val message = Message()
            message.username = modName
            message.avatarUrl = modAvatar
            message.content = "${modName} $version for Minecraft ${mcVersion} has been published!"

            val embed = Embed()
            val downloadSources = StringJoiner("\n")

            if (project(":Fabric").ext.has("curse_file_url")) {

                downloadSources.add("<:fabric:932163720568782878> [Fabric](${project(":Fabric").ext.get("curse_file_url")})")
            }

            if (project(":Forge").ext.has("curse_file_url")) {

                downloadSources.add("<:forge:932163698003443804> [Forge](${project(":Forge").ext.get("curse_file_url")})")
            }

            downloadSources.add(
                "<:maven:932165250738970634> `\"${project(":core").group}:${project(":core").base.archivesName.get()}:${
                    project(":core").version
                }\"`"
            )

            downloadSources.add(
                "<:maven:932165250738970634> `\"${project(":Fabric").group}:${project(":Fabric").base.archivesName.get()}:${
                    project(":Fabric").version
                }\"`"
            )
            downloadSources.add(
                "<:maven:932165250738970634> `\"${project(":Forge").group}:${project(":Forge").base.archivesName.get()}:${
                    project(":Forge").version
                }\"`"
            )

            downloadSources.add(
                "<:maven:932165250738970634> `\"${project(":vanilla").group}:${project(":vanilla").base.archivesName.get()}:${
                    project(":vanilla").version
                }\"`"
            )

            // Add Curseforge DL link if available.
            val downloadString = downloadSources.toString()

            if (downloadString.isNotEmpty()) {

                embed.addField("Download", downloadString, false)
            }

            // Just use the Forge changelog for now, the files are the same anyway.
            embed.addField("Changelog", Utils.getCIChangelog(project, modRepo).take(1000), false)

            embed.color = 0xF16436
            message.addEmbed(embed)

            webhook.sendMessage(message)
        } catch (e: IOException) {

            project.logger.error("Failed to push CF Discord webhook.")
        }
    }

}
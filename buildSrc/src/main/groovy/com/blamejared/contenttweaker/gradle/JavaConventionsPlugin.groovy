package com.blamejared.contenttweaker.gradle

import com.blamejared.modtemplate.ModTemplatePlugin
import com.blamejared.modtemplate.Utils
import com.blamejared.modtemplate.extensions.ModTemplateExtension
import net.darkhax.curseforgegradle.CurseForgeGradlePlugin
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

class JavaConventionsPlugin implements Plugin<Project> {
    private static final DEFAULT_REPOSITORIES = [
            [ 'Sponge', 'https://repo.spongepowered.org/repository/maven-public/' ],
            [ 'BlameJared', 'https://maven.blamejared.com' ],
            [ 'ParchmentMC', 'https://maven.parchmentmc.org' ]
    ]

    @Override
    void apply(final Project target) {
        final ext = target.extensions.findByType ExtraPropertiesExtension

        applyJavaPlugin target, ext
        applyIdeaPlugin target
        applyMavenPlugin target
        applyModTemplate target, ext
        applyCurseTemplate target
        setUpDefaults target, ext
    }

    private static void applyJavaPlugin(final Project project, final ext) {
        project.plugins.apply JavaPlugin

        final java = project.extensions.findByType JavaPluginExtension
        java.toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
        java.withSourcesJar()
        java.withJavadocJar()

        final apiSourceSet = java.sourceSets.create('api') {}
        final apiConfiguration = project.configurations.create('apiConfiguration') {}

        project.afterEvaluate {
            if (project.hasProperty('loom')) {
                project.afterEvaluate {
                    project.configurations.apiImplementation.extendsFrom(project.configurations.minecraftNamed, project.configurations.modImplementationMapped)
                }
            } else {
                project.configurations.apiImplementation.extendsFrom(project.configurations.minecraft)
            }
        }

        project.dependencies.implementation(apiSourceSet.output)
        project.dependencies.apiConfiguration(apiSourceSet.output)

        project.tasks.withType(JavaCompile).configureEach {
            it.options.encoding = StandardCharsets.UTF_8.toString()
            it.options.release.set(ext['java.version'].toInteger())
        }
        project.tasks.withType(Javadoc).configureEach {
            it.options.encoding = StandardCharsets.UTF_8.toString()
        }

        configureJar(project, ext)
    }

    private static void configureJar(final Project project, final ext) {
        project.tasks.withType(Jar).configureEach {
            manifest {
                attributes([
                        'Specification-Title' : ext['mod.name'],
                        'Specification-Vendor' : ext['mod.author'],
                        'Specification-Version' : archiveVersion.get(),
                        'Implementation-Title' : project.name,
                        'Implementation-Version' : archiveVersion.get(),
                        'Implementation-Vendor' : ext['mod.author'],
                        'Implementation-Timestamp' : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()),
                        'Timestamp' : System.currentTimeMillis(),
                        'Built-On-Java' : "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                        'Built-On-Minecraft' : ext['minecraft.version']
                ])
            }
        }
    }

    private static void applyIdeaPlugin(final Project project) {
        project.plugins.apply IdeaPlugin

        final idea = project.extensions.findByType IdeaModel
        idea.module.excludeDirs.addAll([ project.file('run'), project.file('run_server'), project.file('run_client') ])
    }

    private static void applyMavenPlugin(final Project project) {
        project.plugins.apply MavenPublishPlugin

        final publishing = project.extensions.findByType PublishingExtension
        project.afterEvaluate {
            final base = project.extensions.findByType BasePluginExtension
            publishing.publications.register('mavenJava', MavenPublication) {
                artifactId = base.archivesName.get()
                from(project.components['java'])

                if (project.name.equals("Forge")) {
                    pom.withXml {
                        val depNodeList = asNode()["dependencies"] as NodeList
                        depNodeList.map { it as Node }.forEach { depList ->
                            val deps = depList.getAt(QName("http://maven.apache.org/POM/4.0.0", "dependency"))
                            deps.map { it as Node }.forEach { dep ->
                                dep.parent().remove(dep)
                            }
                        }
                    }
                }
            }
        }
        publishing.repositories {
            maven {
                url = "file:///${System.getenv('local_maven')}"
            }
        }
    }

    private static void applyModTemplate(final Project project, final ext) {
        project.plugins.apply ModTemplatePlugin

        final modTemplate = project.extensions.findByType ModTemplateExtension
        modTemplate.mcVersion(ext['minecraft.version'])
        modTemplate.curseHomepage(ext['mod.curse'])
        modTemplate.displayName(ext['mod.name'])
        modTemplate.changelog.with {
            firstCommit(ext['mod.first-commit'])
            repo(ext['mod.repo'])
            changelogFile('changelog.md')
        }
        modTemplate.versionTracker.with {
            endpoint(System.getenv('versionTrackerAPI'))
            author(ext['mod.author'])
            projectName(ext['mod.name'])
            homepage(ext['mod.curse'])
        }
    }

    private static void applyCurseTemplate(final Project project) {
        project.plugins.apply CurseForgeGradlePlugin

        final targetTask = project.tasks.create 'publishToCurseForge', TaskPublishCurseForge
        targetTask.group = 'publishing'
        targetTask.apiToken = System.getenv('curseforgeApiToken') ?: 0
    }

    private static void setUpDefaults(final Project project, final ext) {
        project.version = Utils.updatingSemVersion(ext['mod.version'])

        project.tasks.withType(GenerateModuleMetadata)*.setEnabled(false)

        project.repositories.with {handler ->
            DEFAULT_REPOSITORIES.collect { repo ->
                handler.maven {
                    name = repo[0]
                    url = repo[1]
                }
            }.tap {it.add(mavenCentral()) }
            .each(handler.&add)
        }
    }
}

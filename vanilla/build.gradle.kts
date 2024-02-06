plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    alias(libs.plugins.vanillaGradle)
}

evaluationDependsOn(":core")

minecraft {
    version(libs.versions.minecraft.get())
}

configurations.apiImplementation.configure {
    extendsFrom(configurations.minecraft.get())
}

dependencies {
    apiCompileOnly(libs.crafttweaker.common)
    apiCompileOnly(libs.never.winter)
    apiImplementation(project(":core", "apiConfiguration"))

    compileOnly(libs.crafttweaker.common)
    compileOnly(libs.mixin)
    compileOnly(libs.never.winter)
    implementation(project(":core", "apiConfiguration"))
    implementation(project(":core"))
}

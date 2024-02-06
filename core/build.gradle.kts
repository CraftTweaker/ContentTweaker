plugins {
    id("com.blamejared.contenttweaker.java-conventions")
    alias(libs.plugins.vanillaGradle)
}

minecraft {
    version(libs.versions.minecraft.get())
}

configurations.apiImplementation.configure {
    extendsFrom(configurations.minecraft.get())
}

dependencies {
    apiCompileOnly(libs.crafttweaker.common)
    apiCompileOnly(libs.never.winter)

    compileOnly(libs.crafttweaker.common)
    compileOnly(libs.mixin)
    compileOnly(libs.never.winter)
}

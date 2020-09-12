package com.blamejared.contenttweaker.file_handling;

import com.blamejared.crafttweaker.api.*;

import java.io.*;

public class PackMCMetaTemplate {
    
    private static final String template = "{\n   \"pack\": {\n      \"pack_format\": %d,\n      \"description\": \"%s\"\n   }\n}";
    private static final int format = 6;
    private final String description;
    private final File mcmetaFile;
    
    public PackMCMetaTemplate(File containingDirectory, String description) {
        this.description = description;
        this.mcmetaFile = new File(containingDirectory, "pack.mcmeta");
    }
    
    public void writeIfNotExists() {
        if(!mcmetaFile.exists()) {
            try(final PrintWriter writer = new PrintWriter(new FileWriter(mcmetaFile))) {
                writer.printf(template, format, description);
            } catch(IOException e) {
                CraftTweakerAPI.logThrowing("Could not create pack.mcmeta file!", e);
            }
        }
    }
}

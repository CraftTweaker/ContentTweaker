package com.blamejared.contenttweaker.file_handling;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.file_handling.templates.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraft.util.*;

import java.io.*;

public class PackMCMetaTemplate {
    
    private static final int format = 6;
    
    private final TemplateFile template;
    private final File mcmetaFile;
    
    public PackMCMetaTemplate(File containingDirectory, String description) {
        this.template = TemplateFile.of(ResourceType.DATA, new ResourceLocation(ContentTweaker.MOD_ID, "pack/pack.mcmeta"));
        template.setValue("PACK_FORMAT", String.valueOf(format));
        template.setValue("PACK_DESCRIPTION", description);
        this.mcmetaFile = new File(containingDirectory, "pack.mcmeta");
    }
    
    public void writeIfNotExists() {
        if(!mcmetaFile.exists()) {
            try(final PrintWriter writer = new PrintWriter(new FileWriter(mcmetaFile))) {
                writer.println(template.getContent());
            } catch(IOException e) {
                CraftTweakerAPI.logThrowing("Could not create pack.mcmeta file!", e);
            }
        }
    }
}

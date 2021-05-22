package com.blamejared.contenttweaker.file_handling;

import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.crafttweaker.api.*;
import net.minecraftforge.fml.*;

import javax.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.Collection;

public class ResourcePackInfo {
    
    private final KnownResourceLoaderMod usedModLoader;
    
    public ResourcePackInfo(KnownResourceLoaderMod usedResourceLoader) {
        this.usedModLoader = usedResourceLoader;
    }
    
    @Nullable
    public static ResourcePackInfo get() {
        final ModList modList = ModList.get();
        for(KnownResourceLoaderMod value : KnownResourceLoaderMod.values()) {
            if(modList.isLoaded(value.modid)) {
                CraftTweakerAPI.logDebug("Found loader mod '%s'", value.modid);
                return new ResourcePackInfo(value);
            }
        }
        return null;
    }
    
    public void createResourcePackIfNotExists() {
        createDirectoryIfNotExists(usedModLoader.resourcePackDirectory);
        final PackMCMetaTemplate packMcmeta = new PackMCMetaTemplate(usedModLoader.resourcePackDirectory, "ContentTweaker resources");
        packMcmeta.writeIfNotExists();
    }
    
    public void createDataPackIfNotExists() {
        createDirectoryIfNotExists(usedModLoader.datapackDirectory);
        final PackMCMetaTemplate packMcmeta = new PackMCMetaTemplate(usedModLoader.datapackDirectory, "ContentTweaker loot tables and maybe more");
        packMcmeta.writeIfNotExists();
    }

    public void writeFluidTags(Collection<IIsCotFluid> fluids) {
        new FluidTagsWriter(fluids, usedModLoader.datapackDirectory).write();
    }
    
    private void createDirectoryIfNotExists(File directory) {
        if(!directory.exists()) {
            try {
                Files.createDirectories(directory.toPath());
            } catch(IOException e) {
                CraftTweakerAPI.logThrowing("Could not create directory!", e);
            }
        }
    }
    
    public File getResourcePackDirectory() {
        return usedModLoader.resourcePackDirectory;
    }
    
    public File getDataPackDirectory() {
        return usedModLoader.datapackDirectory;
    }
}

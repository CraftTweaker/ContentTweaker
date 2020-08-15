package com.blamejared.contenttweaker.file_handling;

import java.io.*;

public enum KnownResourceLoaderMod {
    THE_LOADER("theloader", "the_loader/resourcepacks/contenttweaker", "the_loader/datapacks/contenttweaker"),
    OPEN_LOADER("openloader", "openloader/resources/contenttweaker", "openloader/data/contenttweaker");
    
    final String modid;
    final File resourcePackDirectory;
    final File datapackDirectory;
    
    KnownResourceLoaderMod(String modid, String resourcePackDirectory, String datapackDirectory) {
        this.modid = modid;
        this.resourcePackDirectory = new File(resourcePackDirectory);
        this.datapackDirectory = new File(datapackDirectory);
    }
}

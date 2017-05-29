package com.teamacronymcoders.contenttweaker.resources;

import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.base.util.files.ResourcePackAssembler;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@SideOnly(Side.CLIENT)
public class ResourceLoader {
    private static ResourcePackAssembler assembler;
    private static File resourceFolder;

    public static void assembleResourcePack() {
        resourceFolder = ContentTweaker.instance.resourceFolder;
        File resource = resourceFolder.getParentFile();
        assembler = new ResourcePackAssembler(new File(resource, "ContentTweakerResources"),
                "ContentTweaker Resources", MOD_ID);
        createImportantFolders();

        copyFilesFromFolder("", resourceFolder);
        assembler.assemble().inject();
    }

    private static void copyFilesFromFolder(String path, File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    copyFilesFromFolder(path + "/" + file.getName(), file);
                } else {
                    assembler.addFile(path, file);
                }
            }
        }
    }

    private static void createImportantFolders() {
        BaseFileUtils.createFolder(new File(resourceFolder, "lang"));

        File textures = new File(resourceFolder, "textures");
        BaseFileUtils.createFolder(textures);
        BaseFileUtils.createFolder(new File(textures, "blocks"));
        BaseFileUtils.createFolder(new File(textures, "items"));

        BaseFileUtils.createFolder(new File(resourceFolder, "blockstates"));

        File models = new File(resourceFolder, "models");
        BaseFileUtils.createFolder(models);
        BaseFileUtils.createFolder(new File(models, "block"));
        BaseFileUtils.createFolder(new File(models, "item"));
    }

}

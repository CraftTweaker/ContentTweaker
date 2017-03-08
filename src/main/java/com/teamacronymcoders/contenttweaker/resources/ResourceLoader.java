package com.teamacronymcoders.contenttweaker.resources;

import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.base.util.files.ResourcePackAssembler;
import com.teamacronymcoders.base.util.files.ResourcePackAssembler.ModelType;
import com.teamacronymcoders.contenttweaker.ContentTweaker;

import java.io.File;
import java.io.FileFilter;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;
import static com.teamacronymcoders.contenttweaker.resources.BaseFileFilters.*;

public class ResourceLoader {
    private static ResourcePackAssembler assembler;
    private static File resourceFolder;

    public static void assembleResourcePack() {
        resourceFolder = ContentTweaker.instance.resourceFolder;
        File resource = resourceFolder.getParentFile();
        assembler = new ResourcePackAssembler(new File(resource, "ContentTweakerResources"),
                "ContentTweaker Resources", MOD_ID);
        addModels();
        addTextures();
        addLang();
        assembler.assemble().inject();
    }

    private static void addModels() {
        addModels(ModelType.BLOCKSTATE, "blockstates");
        File models = new File(resourceFolder, "models");
        BaseFileUtils.createFolder(models);
        addModels(ModelType.BLOCK, "models/block");
        addModels(ModelType.ITEM, "models/item");
    }

    private static void addModels(ModelType modelType, String path) {
        File models = new File(resourceFolder, path);
        BaseFileUtils.createFolder(models);
        File[] modelFiles = models.listFiles(jsonFilter);
        if (modelFiles != null) {
            for (File file : modelFiles) {
                assembler.addModel(file, modelType);
            }
        }
    }

    private static void addTextures() {
        File textures = new File(resourceFolder, "textures");
        BaseFileUtils.createFolder(textures);
        File[] textureFiles = textures.listFiles((FileFilter) textureFilter);
        if (textureFiles != null) {
            for (File file : textureFiles) {
                assembler.addIcon(file);
            }
        }
    }

    private static void addLang() {
        File lang = new File(resourceFolder, "lang");
        BaseFileUtils.createFolder(lang);
        File[] langFiles = lang.listFiles(langFilter);
        if (langFiles != null) {
            for (File file : langFiles) {
                assembler.addLang(file);
            }
        }
    }

}

package com.teamacronymcoders.tailoredobjects.loader;

import com.teamacronymcoders.base.reference.Reference;
import com.teamacronymcoders.base.util.files.ResourcePackAssembler;
import com.teamacronymcoders.base.util.logging.ILogger;

import java.io.File;

public class ClientObjectLoader extends ObjectLoader {
    private ResourcePackAssembler assembler;

    protected File modelsFolder;
    protected File texturesFolder;
    protected File langFolder;

    public ClientObjectLoader(ILogger logger, File configFolderLocation) {
        super(logger, configFolderLocation);
        assembler = new ResourcePackAssembler(overallFolder, "CustomObjects", Reference.MODID);
    }

    protected boolean createFolders(File parentDirectory) {
        boolean allOkay = super.createFolders(parentDirectory);
        modelsFolder = new File(parentDirectory, "models");
        texturesFolder = new File(parentDirectory, "textures");

        if (!modelsFolder.exists()) {
            allOkay &= modelsFolder.mkdirs();
        }
        if (!texturesFolder.exists()) {
            allOkay &= texturesFolder.mkdirs();
        }

        return allOkay;
    }

    public void load(ObjectRepository repository) {
        loadModel(modelsFolder, repository);
    }

    private void loadModel(File modelsFolder, ObjectRepository repository) {

    }
}

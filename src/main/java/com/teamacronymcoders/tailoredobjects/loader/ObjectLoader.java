package com.teamacronymcoders.tailoredobjects.loader;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.teamacronymcoders.tailoredobjects.representations.FileRepresentation;
import com.teamacronymcoders.base.util.logging.ILogger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ObjectLoader {
    protected ILogger logger;

    protected File overallFolder;
    protected File objectsFolder;

    public ObjectLoader(ILogger logger, File configFolderLocation) {
        this.logger = logger;
        boolean allOkay = createFolders(new File(configFolderLocation, "customobjects"));
        if (!allOkay) {
            logger.fatal("NOT ALL FOLDERS WERE CREATED PROPERLY THIS COULD CAUSE ISSUES");
        }
    }

    protected boolean createFolders(File parentDirectory) {
        overallFolder = parentDirectory;
        objectsFolder = new File(parentDirectory, "objects");

        boolean allOkay = true;
        if (!objectsFolder.exists()) {
            allOkay = objectsFolder.mkdirs();
        }

        return allOkay;
    }

    public void load(ObjectRepository repository) {
        loadObjects(objectsFolder, repository);
    }

    private void loadObjects(File objectFolder, ObjectRepository repository) {
        if (objectFolder.exists() && objectFolder.isDirectory()) {
            File[] objectJsons = objectFolder.listFiles(pathname ->
                    Files.getFileExtension(pathname.getAbsolutePath()).equalsIgnoreCase("json"));
            if (objectJsons != null) {
                Gson gson = new Gson();
                for (File objectFile : objectJsons) {
                    try {
                        String jsonString = Files.toString(objectFile, Charset.forName("UTF-8"));
                        FileRepresentation fileRepresentation = gson.fromJson(jsonString, FileRepresentation.class);
                        if(fileRepresentation.getBlocks() != null) {
                            fileRepresentation.getBlocks().forEach(repository::addBlock);
                        }
                        if(fileRepresentation.getItems() != null) {
                            fileRepresentation.getItems().forEach(repository::addItem);
                        }
                        if(fileRepresentation.getTileEntities() != null) {
                            fileRepresentation.getTileEntities().forEach(repository::addTile);
                        }
                        if(fileRepresentation.getBlockCommands() != null) {
                            fileRepresentation.getBlockCommands().forEach(repository::addBlockCommand);
                        }
                        if(fileRepresentation.getItemCommands() != null) {
                            fileRepresentation.getItemCommands().forEach(repository::addItemCommand);
                        }
                        if(fileRepresentation.getTileEntityCommands() != null) {
                            fileRepresentation.getTileEntityCommands().forEach(repository::addTileCommand);
                        }
                        if(fileRepresentation.getItemStacks() != null) {
                            fileRepresentation.getItemStacks().forEach(repository::addItemStack);
                        }
                    } catch (IOException e) {
                        logger.getLogger().error(String.format("File named %s did not deserialize properly",
                                objectFile.getName()), e);
                    }
                }
            }
        } else {
            logger.fatal("Objects Folder not found. Can't load objects");
        }
    }
}

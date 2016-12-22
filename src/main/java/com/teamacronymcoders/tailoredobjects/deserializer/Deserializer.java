package com.teamacronymcoders.tailoredobjects.deserializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.teamacronymcoders.base.util.logging.ILogger;
import com.teamacronymcoders.tailoredobjects.TailoredObjects;
import com.teamacronymcoders.tailoredobjects.api.TailoredObjectsAPI;
import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;
import com.teamacronymcoders.tailoredobjects.api.deserializer.IDeserializer;
import com.teamacronymcoders.tailoredobjects.representations.ObjectRepresentation;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Deserializer {
    public Gson fileGson;
    public File file;
    public DeserializerRegistry deserializerRegistry;
    public ILogger logger;

    public Deserializer(File file) {
        this.fileGson = new Gson();
        this.file = file;
        this.logger = TailoredObjects.instance.getLogger();
        this.deserializerRegistry = TailoredObjectsAPI.getInstance().getDeserializerRegistry();
    }

    public void deserialize() {
        this.deserialize(file);
    }

    private void deserialize(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            if(files != null) {
                Arrays.stream(files).forEach(this::deserialize);
            }
        } else {
            try {
                String jsonString = FileUtils.readFileToString(file);
                Type listType = new TypeToken<List<ObjectRepresentation>>(){}.getType();
                List<ObjectRepresentation> objectRepresentations = this.fileGson.fromJson(jsonString, listType);
                objectRepresentations.forEach(objectRepresentation -> {
                    IDeserializer deserializer = this.deserializerRegistry.getDeserializer(objectRepresentation.getType());
                    if(deserializer != null) {
                        boolean success = deserializer.deserializeObject(this.fileGson, objectRepresentation.getObject());
                        if(!success) {
                            logger.error("Object " + objectRepresentation.getName() + " failed to deserialize");
                        }
                    } else {
                        logger.error("No deserializer type " + objectRepresentation.getName() + " found for " +
                                objectRepresentation.getName());
                    }
                });
            } catch (IOException e) {
                logger.getLogger().error("File " + file.getName() + "failed to deserialize", e);
            }
        }
    }
}

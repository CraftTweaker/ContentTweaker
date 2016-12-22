package com.teamacronymcoders.tailoredobjects.deserializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.teamacronymcoders.tailoredobjects.TailoredObjects;
import com.teamacronymcoders.tailoredobjects.api.TailoredObjectsAPI;
import com.teamacronymcoders.tailoredobjects.api.deserializer.IDeserializer;
import com.teamacronymcoders.tailoredobjects.representations.ObjectRepresentation;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Deserializer {
    public Gson fileGson;
    public File file;
    public Map<String, IDeserializer> deserializers;

    public Deserializer(File file) {
        this.fileGson = new Gson();
        this.file = file;
        this.deserializers = TailoredObjectsAPI.getInstance().getDeserializerRegistry().getDeserializers();
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
                    IDeserializer deserializer = this.deserializers.get(objectRepresentation.getType());
                    boolean successful = deserializer.deserializeObject(objectRepresentation.getObject());
                    if(!successful) {
                        TailoredObjects.instance.getLogger().error("Object " + objectRepresentation.getName() +
                                " failed to deserialize");
                    }
                });
            } catch (IOException e) {
                TailoredObjects.instance.getLogger().getLogger().error("File " + file.getName() +
                        " failed to deserialize", e);
            }
        }
    }
}

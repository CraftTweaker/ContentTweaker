package com.teamacronymcoders.contenttweaker.modules.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.teamacronymcoders.base.util.logging.ILogger;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.deserializer.DeserializerRegistry;
import com.teamacronymcoders.contenttweaker.api.deserializer.IDeserializer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deserializer {
    public Gson fileGson;
    public File file;
    public DeserializerRegistry deserializerRegistry;
    public ILogger logger;

    public Deserializer(File file) {
        this.fileGson = new Gson();
        this.file = file;
        this.logger = ContentTweaker.instance.getLogger();
        this.deserializerRegistry = ContentTweakerAPI.getInstance().getDeserializerRegistry();
    }

    public void deserialize() {
        this.deserialize(file);
    }

    private void deserialize(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(this::deserialize);
            }
        } else {
            try {
                String jsonString = FileUtils.readFileToString(file);
                Type listType = new TypeToken<List<ContentHolder>>() {}.getType();
                List<ContentHolder> contentHolders = this.fileGson.fromJson(jsonString, listType);
                Map<IDeserializer, List<ContentHolder>> deserializersAndContent = new HashMap<>();
                contentHolders.forEach(contentHolder -> {
                    IDeserializer deserializer = this.deserializerRegistry.getDeserializer(contentHolder.getType());
                    if (deserializer != null) {
                        if (!deserializersAndContent.containsKey(deserializer)) {
                            deserializersAndContent.put(deserializer, new ArrayList<>());
                        }
                        deserializersAndContent.get(deserializer).add(contentHolder);
                    }
                });
                deserializeObjects(deserializersAndContent, true);
                deserializeObjects(deserializersAndContent, false);

            } catch (IOException e) {
                logger.getLogger().error("File " + file.getName() + "failed to deserialize", e);
            }
        }
    }

    public void deserializeObjects(Map<IDeserializer, List<ContentHolder>> deserializersAndContent, boolean isMaterial) {
        deserializersAndContent.entrySet().stream().filter(entry -> entry.getKey().isMaterial() == isMaterial)
                .forEach(entry -> entry.getValue().forEach(contentHolder -> {
                    boolean success = entry.getKey().deserializeObject(contentHolder.getContent());
                    if (!success) {
                        logger.error("Object " + contentHolder.getName() + " failed to deserialize");
                    }
                }));
    }
}

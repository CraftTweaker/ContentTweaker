package com.blamejared.contenttweaker.file_handling.templates;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.resources.*;
import net.minecraft.util.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class TemplateFile {
    
    public static final TemplateFile EMPTY = new TemplateFile(new ResourceLocation(ContentTweaker.MOD_ID, "templates/empty"), "") {
        @Override
        public void setValue(String key, String value) {
        }
    };
    private static final String delimiter = "$$";
    private final ResourceLocation location;
    private String template;
    
    private TemplateFile(ResourceLocation location, String template) {
        this.template = template;
        this.location = location;
    }
    
    public static TemplateFile of(ResourceType type, ResourceLocation location) {
        final String template = read(type, location);
        if(template == null) {
            return EMPTY;
        }
        return new TemplateFile(location, template);
    }
    
    private static String read(ResourceType type, ResourceLocation location) {
        final String format = "/%s/%s/templates/%s.json";
        final String folderName = type.getFolderName();
        final String path = String.format(format, folderName, location.getNamespace(), location.getPath());
        
        final InputStream resourceAsStream = TemplateFile.class.getResourceAsStream(path);
        if(resourceAsStream == null) {
            return null;
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
    
    public TemplateFile copy() {
        return this == EMPTY ? this : new TemplateFile(location, template);
    }
    
    public void setValues(Map<String, String> keyValuePairs) {
        keyValuePairs.forEach(this::setValue);
    }
    
    public void setValue(String key, String value) {
        final String embeddedKey = delimiter + key + delimiter;
        if(!template.contains(embeddedKey)) {
            throw new IllegalArgumentException(String.format("Invalid key for template %s: '%s'", location, key));
        }
        template = template.replace(embeddedKey, value);
    }
    
    public byte[] getContentArray() {
        return template.getBytes();
    }
    
    public String getContent() {
        return template;
    }
}

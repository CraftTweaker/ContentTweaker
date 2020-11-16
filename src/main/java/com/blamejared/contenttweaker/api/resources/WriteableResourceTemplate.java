package com.blamejared.contenttweaker.api.resources;

import com.blamejared.contenttweaker.file_handling.templates.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

public class WriteableResourceTemplate extends WriteableResource {
    
    private TemplateFile templateFile;
    
    public WriteableResourceTemplate(ResourceType type, MCResourceLocation location, String... prefixes) {
        super(type, FileExtension.JSON, location, prefixes);
        this.contentSupplier = this::getContentArray;
    }
    
    public WriteableResourceTemplate(ResourceType type, String namespace, String path, String... prefixes) {
        this(type, new MCResourceLocation(namespace, path), prefixes);
    }
    
    private byte[] getContentArray() {
        return templateFile.getContentArray();
    }
    
    public WriteableResourceTemplate withTemplate(ResourceType type, ResourceLocation location) {
        this.templateFile = TemplateFile.of(type, location);
        return this;
    }
    
    public WriteableResourceTemplate setProperty(String key, String value) {
        templateFile.setValue(key, value);
        return this;
    }
    
    /**
     * Utility method that sets both NAMESPACE and PATH at once
     */
    public WriteableResourceTemplate setLocationProperty(MCResourceLocation location) {
        return setProperty("NAMESPACE", location.getNamespace()).setProperty("PATH", location.getPath());
    }
    
    public WriteableResourceTemplate setLocationProperty(MCResourceLocation location, String suffix) {
        final String namespace = location.getNamespace();
        final String path = location.getPath();
        return setProperty("NAMESPACE" + "_" + suffix, namespace).setProperty("PATH" + "_" + suffix, path);
    }
}

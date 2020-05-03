package com.blamejared.contenttweaker.api.resources;

public enum FileExtension {
    JSON("json"), PNG("png");
    
    private final String extension;
    
    FileExtension(String extension) {
        this.extension = extension;
    }
    
    public String getExtension() {
        return extension;
    }
    
    @Override
    public String toString() {
        return getExtension();
    }
}

package com.blamejared.contenttweaker.api.resources;

public enum ResourceType {
    ASSETS("assets"),
    DATA("data");
    
    private final String folderName;
    
    private ResourceType(String folderName) {
        this.folderName = folderName;
    }
    
    public String getFolderName() {
        return folderName;
    }
    
    @Override
    public String toString() {
        return getFolderName();
    }
}

package com.blamejared.contenttweaker.blocks.types.machine.item;

import java.util.*;

public class GroupNames {
    private final Set<String> names = new HashSet<>();
    
    public void addName(String name) {
        names.add(name);
    }
    
    public boolean hasName(String name) {
        return names.contains(name);
    }
    
    public void removeName(String name) {
        names.remove(name);
    }
}

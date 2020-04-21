package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemRepresentation")
public abstract class MCItemRepresentation {
    
    private final MCProperties properties;
    
    @ZenCodeType.Constructor
    public MCItemRepresentation() {
        this(new MCProperties());
    }
    
    @ZenCodeType.Constructor
    public MCItemRepresentation(MCProperties properties) {
        this.properties = properties;
    }
    
    @ZenCodeType.Getter("properties")
    public MCProperties getProperties() {
        return properties;
    }
    
    public abstract String getDisplayName();
}

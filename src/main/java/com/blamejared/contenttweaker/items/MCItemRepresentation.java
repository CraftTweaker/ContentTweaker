package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemRepresentation")
public class MCItemRepresentation {
    
    @ZenCodeType.Field
    public final MCItemProperties properties;
    
    @ZenCodeType.Constructor
    public MCItemRepresentation() {
        this(new MCItemProperties());
    }
    
    @ZenCodeType.Constructor
    public MCItemRepresentation(MCItemProperties properties) {
        this.properties = properties;
    }
    
    @ZenCodeType.Method
    public MCItemProperties getProperties() {
        return properties;
    }
    
    @ZenCodeType.Getter("canRepair")
    public boolean canRepair() {
        return true;
    }
}

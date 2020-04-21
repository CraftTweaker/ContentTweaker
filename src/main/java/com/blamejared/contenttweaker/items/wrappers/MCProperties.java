package com.blamejared.contenttweaker.items.wrappers;

import net.minecraft.item.*;
import org.openzen.zencode.java.*;

public class MCProperties {
    
    private final Item.Properties internal;
    
    @ZenCodeType.Constructor
    public MCProperties(Item.Properties internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCProperties() {
        this(new Item.Properties());
    }
    
    public Item.Properties getInternal() {
        return internal;
    }
}

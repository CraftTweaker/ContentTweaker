package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.ItemTypeBuilder")
public abstract class ItemTypeBuilder implements IIsBuilder {
    
    protected final ItemBuilder itemBuilder;
    
    public ItemTypeBuilder(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }
    
    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }
}

package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

/**
 * Denotes a special builder that is used for builing special item types.
 * Used in {@link ItemBuilder#withType}
 *
 * @docParam this new ItemBuilder().withType<ItemBuilderBasic>()
 */
@ZenRegister
@Document("mods/contenttweaker/API/item/ItemTypeBuilder")
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

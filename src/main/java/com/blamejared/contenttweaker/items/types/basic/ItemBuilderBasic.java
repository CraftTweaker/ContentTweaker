package com.blamejared.contenttweaker.items.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

/**
 * The basic builder for items, also called by {@link ItemBuilder#build}.
 * Does not have any special properties, it exists.
 *
 * @docParam this new ItemBuilder().withType<ItemBuilderBasic>()
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.basic.ItemBuilderBasic")
@Document("mods/contenttweaker/API/item/basic/ItemBuilderBasic")
public class ItemBuilderBasic extends ItemTypeBuilder {
    
    
    public ItemBuilderBasic(ItemBuilder builder) {
        super(builder);
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTItemBasic itemBasic = new CoTItemBasic(itemBuilder.getItemProperties(), location);
        VanillaFactory.queueItemForRegistration(itemBasic);
    }
}

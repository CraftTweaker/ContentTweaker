package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.types.basic.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;

@ZenRegister
@Document("mods/contenttweaker/item/ItemBuilder")
@ZenCodeType.Name("mods.contenttweaker.item.ItemBuilder")
public class ItemBuilder implements IIsBuilder {
    
    private final Item.Properties itemProperties;
    
    @ZenCodeType.Constructor
    public ItemBuilder() {
        itemProperties = new Item.Properties().group(ItemGroup.MISC);
    }
    
    public Item.Properties getItemProperties() {
        return itemProperties;
    }
    
    @ZenCodeType.Method
    public ItemBuilder withMaxStackSize(int maxStackSize) {
        itemProperties.maxStackSize(maxStackSize);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemBuilder withMaxDamage(int maxDamage) {
        itemProperties.maxDamage(maxDamage);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemBuilder withItemGroup(MCItemGroup itemGroup) {
        itemProperties.group(itemGroup.getInternal());
        return this;
    }
    
    @ZenCodeType.Method
    public ItemBuilder withRarity(String rarity) {
        itemProperties.rarity(Rarity.valueOf(rarity));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemBuilder withNoRepair() {
        itemProperties.setNoRepair();
        return this;
    }
    
    @ZenCodeType.Method
    public <T extends ItemTypeBuilder> T withType(Class<T> typeOfT) {
        try {
            final Constructor<T> constructor = typeOfT.getConstructor(ItemBuilder.class);
            return constructor.newInstance(this);
        } catch(InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        withType(BuilderBasic.class).build(location);
    }
}

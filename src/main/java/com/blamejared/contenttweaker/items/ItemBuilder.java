package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.types.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
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
    public <T extends IIsBuilder> T withType(IItemTypeSpecifier<T> specifier) {
        return specifier.apply(this);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        withType(ItemTypeSpecifiers.basic).build(location);
    }
}

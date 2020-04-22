package com.blamejared.contenttweaker.items.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.food.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemProperties")
public class MCItemProperties {
    
    @ZenCodeType.Field
    public MCItemGroup itemGroup = new MCItemGroup(ItemGroup.SEARCH);
    
    @ZenCodeType.Field
    public int maxStackSize = 64;
    
    @ZenCodeType.Field
    public int maxDamage = 0;
    
    @ZenCodeType.Field
    public MCFood food;
    
    @ZenCodeType.Field
    public boolean canRepair = true;
    
    
    @ZenCodeType.Constructor
    public MCItemProperties() {
    }
    
    
    @ZenCodeType.Method
    public MCItemProperties setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties setFood(MCFood food) {
        this.food = food;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties setCanRepair(boolean canRepair) {
        this.canRepair = canRepair;
        return this;
    }
    
    public Item.Properties createInternal() {
        Item.Properties properties = new Item.Properties()
                .group(itemGroup.getInternal())
                .maxStackSize(maxStackSize);
        
        if(maxDamage != 0) {
            properties = properties.maxDamage(maxDamage);
        }
        
        if(!canRepair) {
            properties = properties.setNoRepair();
        }
        if(food != null) {
            properties = properties.food(food.getInternal());
        }
        return properties;
    }
}

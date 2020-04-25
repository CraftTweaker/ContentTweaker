package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.items.functions.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.food.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemProperties")
public class MCItemProperties {
    
    private final Map<ToolType, ToolTypeFunction> toolClasses = new HashMap<>();
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
    @ZenCodeType.Field
    public ToolDestroySpeedFunction destroySpeedFunction = (stack, state) -> 1.0f;
    
    @ZenCodeType.Constructor
    public MCItemProperties() {
    }
    
    public Map<ToolType, ToolTypeFunction> getToolClasses() {
        return toolClasses;
    }
    
    @ZenCodeType.Method
    public MCItemProperties withMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties withMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties withFood(MCFood food) {
        this.food = food;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties withCanRepair(boolean canRepair) {
        this.canRepair = canRepair;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties withItemGroup(MCItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties isTool(MCToolType type, ToolTypeFunction fun) {
        toolClasses.put(type.getInternal(), fun);
        return this;
    }
    
    @ZenCodeType.Method
    public MCItemProperties isTool(MCToolType type, int harvestLevel) {
        return isTool(type, (stack, type1, player, blockState) -> harvestLevel);
    }
    
    @ZenCodeType.Method
    public MCItemProperties withDestroySpeed(ToolDestroySpeedFunction fun) {
        destroySpeedFunction = fun;
        return this;
    }
    
    @ZenCodeType.Method
    public void build(String name) {
        VanillaFactory.registerItem(new CoTItem(this), new MCResourceLocation(new ResourceLocation(ContentTweaker.MOD_ID, name)));
    }
    
    public Item.Properties createInternal() {
        Item.Properties properties = new Item.Properties().group(itemGroup.getInternal())
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

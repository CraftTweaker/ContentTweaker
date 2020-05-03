package com.blamejared.contenttweaker.items.types.tool;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.tool.BuilderTool")
public class BuilderTool implements IIsBuilder {
    
    private final ItemBuilder builder;
    private final Map<ToolType, Float> miningSpeeds;
    private double attackSpeed;
    private double attackDamage;
    
    public BuilderTool(ItemBuilder builder) {
        this.builder = builder;
        this.miningSpeeds = new HashMap<>();
    }
    
    public Map<ToolType, Float> getMiningSpeeds() {
        return miningSpeeds;
    }
    
    public ItemBuilder getBuilder() {
        return builder;
    }
    
    @ZenCodeType.Method
    public BuilderTool withToolType(MCToolType toolType, int miningLevel, @ZenCodeType.OptionalFloat(1.0F) float miningSpeed) {
        builder.getItemProperties().addToolType(toolType.getInternal(), miningLevel);
        miningSpeeds.put(toolType.getInternal(), miningSpeed);
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderTool withAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderTool withAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerItem(new CoTItemTool(this, location.getInternal()));
    }
    
    public double getAttackSpeed() {
        return attackSpeed;
    }
    
    public double getAttackDamage() {
        return attackDamage;
    }
}

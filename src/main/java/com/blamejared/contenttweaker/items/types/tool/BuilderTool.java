package com.blamejared.contenttweaker.items.types.tool;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.tool.BuilderTool")
@Document("mods/contenttweaker/item/tool/BuilderTool")
public class BuilderTool extends ItemTypeBuilder {
    
    private final Map<ToolType, Float> miningSpeeds;
    private double attackSpeed;
    private double attackDamage;
    private int durabilityCostAttack;
    private int durabilityCostMining;
    
    public BuilderTool(ItemBuilder builder) {
        super(builder);
        this.miningSpeeds = new HashMap<>();
        this.durabilityCostAttack = 2;
        this.durabilityCostMining = 1;
    }
    
    public Map<ToolType, Float> getMiningSpeeds() {
        return miningSpeeds;
    }
    
    @ZenCodeType.Method
    public BuilderTool withToolType(MCToolType toolType, int miningLevel, @ZenCodeType.OptionalFloat(1.0F) float miningSpeed) {
        itemBuilder.getItemProperties().addToolType(toolType.getInternal(), miningLevel);
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
    
    @ZenCodeType.Method
    public BuilderTool withDurabilityCostAttack(int durabilityCostAttack) {
        this.durabilityCostAttack = durabilityCostAttack;
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderTool withDurabilityCostMining(int durabilityCostMining) {
        this.durabilityCostMining = durabilityCostMining;
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
    
    public int getDurabilityCostAttack() {
        return durabilityCostAttack;
    }
    
    public int getDurabilityCostMining() {
        return durabilityCostMining;
    }
}

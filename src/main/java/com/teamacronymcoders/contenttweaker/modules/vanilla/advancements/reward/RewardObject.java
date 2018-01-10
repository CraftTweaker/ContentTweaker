package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.reward;

import net.minecraft.command.FunctionObject;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.contenttweaker.RewardObject")
public class RewardObject {

    @ZenProperty
    private int experience;
    @ZenProperty
    private String[] loot;
    @ZenProperty
    private String[] recipes;
    @ZenProperty
    private FunctionObject.CacheableFunction function;

    @ZenMethod
    public void setFunction(FunctionObject.CacheableFunction function) {
        this.function = function;
    }
    @ZenMethod
    public FunctionObject.CacheableFunction getFunction() {
        return function;
    }
    @ZenMethod
    public void setRecipes(String[] recipes) {
        this.recipes = recipes;
    }
    @ZenMethod
    public String[] getRecipes() {
        return recipes;
    }
    @ZenMethod
    public void setLoot(String[] loot) {
        this.loot = loot;
    }
    @ZenMethod
    public String[] getLoot() {
        return loot;
    }
    @ZenMethod
    public void setExperience(int experience) {
        this.experience = experience;
    }
    @ZenMethod
    public int getExperience() {
        return experience;
    }
}

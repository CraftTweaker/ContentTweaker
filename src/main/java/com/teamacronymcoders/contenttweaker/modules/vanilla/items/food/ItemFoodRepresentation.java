package com.teamacronymcoders.contenttweaker.modules.vanilla.items.food;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import net.minecraft.item.EnumAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.contenttweaker.ItemFood")
public class ItemFoodRepresentation extends ItemRepresentation {
    @ZenProperty
    public int healAmount;
    @ZenProperty
    public boolean alwaysEditable = false;
    @ZenProperty
    public boolean wolfFood = false;
    @ZenProperty
    public float saturation = 0.6f;
    @ZenProperty
    public String itemUseAction = EnumAction.EAT.toString();

    @ZenMethod
    public int getHealAmount() {
        return healAmount;
    }

    @ZenMethod
    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    @ZenMethod
    public boolean isAlwaysEditable() {
        return alwaysEditable;
    }

    @ZenMethod
    public void setAlwaysEditable(boolean alwaysEditable) {
        this.alwaysEditable = alwaysEditable;
    }

    @ZenMethod
    public boolean isWolfFood() {
        return wolfFood;
    }

    @ZenMethod
    public void setWolfFood(boolean wolfFood) {
        this.wolfFood = wolfFood;
    }

    @ZenMethod
    public float getSaturation() {
        return saturation;
    }

    @ZenMethod
    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    @ZenMethod
    public String getItemUseAction() {
        return itemUseAction;
    }

    @ZenMethod
    public void setItemUseAction(String itemUseAction) {
        this.itemUseAction = itemUseAction;
    }

    @Override
    public String getTypeName() {
        return "ItemFood";
    }

    @ZenMethod
    public void register() {
        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM").register(new ItemContentFood(this));
    }
}

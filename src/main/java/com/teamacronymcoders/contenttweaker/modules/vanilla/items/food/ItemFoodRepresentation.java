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
    public ItemFoodRepresentation() {
        super();
        this.itemUseAction = EnumAction.EAT.toString();
    }

    @ZenProperty
    public int healAmount;
    @ZenProperty
    public boolean alwaysEditable = false;
    @ZenProperty
    public boolean wolfFood = false;
    @ZenProperty
    public float saturation = 0.6f;
    @ZenProperty
    public boolean alwaysEdible = false;

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
    public boolean isAlwaysEdible() {
        return alwaysEdible;
    }

    @ZenMethod
    public void setAlwaysEdible(boolean alwaysEdible) {
        this.alwaysEdible = alwaysEdible;
    }

    @Override
    public String getTypeName() {
        return "ItemFood";
    }

    @ZenMethod
    public void register() {
        if (alwaysEditable) {
            this.alwaysEdible = true;
        }
        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM").register(new ItemContentFood(this));
    }
}

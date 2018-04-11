package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.contenttweaker.enchantments.EnchantmentBuilder")
@ZenRegister
public class EnchantmentBuilder {

    private Enchantment.Rarity rarity = Enchantment.Rarity.COMMON;
    private EnumEnchantmentType type = EnumEnchantmentType.ALL;

    @ZenProperty
    public String name;

    @ZenProperty
    public IEntityEquipmentSlot[] applicableSlots = new IEntityEquipmentSlot[0];

    @ZenProperty
    public boolean treasure = false;

    @ZenProperty
    public boolean curse = false;

    @ZenProperty
    public boolean allowedOnBooks = true;

    @ZenProperty
    public int minLevel = 1;

    @ZenProperty
    public int maxLevel = 1;

    @ZenProperty
    public EnchantmentFunctions.CalcCanApply canApply = null;

    @ZenProperty
    public EnchantmentFunctions.CalcCanApply canApplyAtEnchantmentTable = null;

    @ZenProperty
    public EnchantmentFunctions.CalcCanApplyTogether canApplyTogether = null;

    @ZenProperty
    public EnchantmentFunctions.CalcDamageByCreature calcDamageByCreature = null;

    @ZenProperty
    public EnchantmentFunctions.CalcEnchantability calcEnchantabilityMin = null;

    @ZenProperty
    public EnchantmentFunctions.CalcEnchantability calcEnchantabilityMax = null;

    @ZenProperty
    public EnchantmentFunctions.CalcModifierDamage calcModifierDamage = null;

    @ZenProperty
    public EnchantmentFunctions.CalcTranslatedName calcTranslatedName = null;

    @ZenProperty
    public EnchantmentFunctions.OnEntityDamaged onEntityDamaged = null;

    @ZenProperty
    public EnchantmentFunctions.OnUserHurt onUserHurt = null;

    @ZenProperty
    public String domain = "contenttweaker";

    public EnchantmentBuilder(String name) {
        this.name = name;
    }

    @ZenMethod
    public static EnchantmentBuilder create(String name) {
        return new EnchantmentBuilder(name);
    }


    @ZenMethod
    public void setRarityCommon() {
        rarity = Enchantment.Rarity.COMMON;
    }

    @ZenMethod
    public void setRarityUncommon() {
        rarity = Enchantment.Rarity.UNCOMMON;
    }

    @ZenMethod
    public void setRarityRare() {
        rarity = Enchantment.Rarity.RARE;
    }

    @ZenMethod
    public void setRarityVeryRare() {
        rarity = Enchantment.Rarity.VERY_RARE;
    }

    @ZenSetter("rarity")
    public void setRarity(String rarity) {
        this.rarity = Enchantment.Rarity.valueOf(rarity);
    }

    @ZenGetter("rarity")
    public String getRarity() {
        return rarity.name();
    }

    @ZenMethod
    public void setTypeAll() {
        type = EnumEnchantmentType.ALL;
    }

    @ZenMethod
    public void setTypeArmor() {
        type = EnumEnchantmentType.ARMOR;
    }

    @ZenMethod
    public void setTypeFeed() {
        type = EnumEnchantmentType.ARMOR_FEET;
    }

    @ZenMethod
    public void setTypeLegs() {
        type = EnumEnchantmentType.ARMOR_LEGS;
    }

    @ZenMethod
    public void setTypeChest() {
        type = EnumEnchantmentType.ARMOR_CHEST;
    }

    @ZenMethod
    public void setTypeHead() {
        type = EnumEnchantmentType.ARMOR_HEAD;
    }

    @ZenMethod
    public void setTypeWeapon() {
        type = EnumEnchantmentType.WEAPON;
    }

    @ZenMethod
    public void setTypeDigger() {
        type = EnumEnchantmentType.DIGGER;
    }

    @ZenMethod
    public void setTypeFishingRod() {
        type = EnumEnchantmentType.FISHING_ROD;
    }

    @ZenMethod
    public void setTypeBreakable() {
        type = EnumEnchantmentType.BREAKABLE;
    }

    @ZenMethod
    public void setTypeBow() {
        type = EnumEnchantmentType.BOW;
    }

    @ZenMethod
    public void setTypeWearable() {
        type = EnumEnchantmentType.WEARABLE;
    }

    @ZenGetter("type")
    public String getType() {
        return type.name();
    }

    @ZenSetter("type")
    public void setType(String type) {
        this.type = EnumEnchantmentType.valueOf(type);
    }


    @ZenMethod
    public IEnchantmentDefinition register() {
        CoTEnchantment enchantment = new CoTEnchantment(rarity, type, applicableSlots);

        //Basics
        enchantment.minLevel = this.minLevel;
        enchantment.maxLevel = this.maxLevel;
        enchantment.allowedOnBooks = this.allowedOnBooks;
        enchantment.curse = this.curse;
        enchantment.treasure = this.treasure;

        //Calc
        enchantment.apply = this.canApply;
        enchantment.applyAtEnchTable = this.canApplyAtEnchantmentTable;
        enchantment.canApplyTogether = this.canApplyTogether;
        enchantment.damageByCreature = this.calcDamageByCreature;
        enchantment.enchantabilityMin = this.calcEnchantabilityMin;
        enchantment.enchantabilityMax = this.calcEnchantabilityMax;
        enchantment.modifierDamage = this.calcModifierDamage;
        enchantment.translatedName = this.calcTranslatedName;

        //Events
        enchantment.onEntityDamaged = this.onEntityDamaged;
        enchantment.onUserHurt = this.onUserHurt;


        //Registering
        if (name == null) {
            CraftTweakerAPI.logError("Trying to register an Enchantment with null name! Aborting...");
        } else {
            enchantment.setName(name);
            enchantment.setRegistryName(domain, name);
            ForgeRegistries.ENCHANTMENTS.register(enchantment);
        }
        return enchantment.thisDefinition;
    }

}

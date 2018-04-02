package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.enchantments.MCEnchantmentDefinition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class CoTEnchantment extends Enchantment {

    public int minLevel = 0, maxLevel = 0;
    public boolean curse = false, treasure = false, allowedOnBooks = true;
    public EnchantmentFunctions.CalcCanApply apply = null, applyAtEnchTable = null;

    public EnchantmentFunctions.CalcCanApplyTogether canApplyTogether = null;
    public EnchantmentFunctions.CalcDamageByCreature damageByCreature = null;
    public EnchantmentFunctions.CalcEnchantability enchantabilityMin = null, enchantabilityMax = null;
    public EnchantmentFunctions.CalcModifierDamage modifierDamage = null;
    public EnchantmentFunctions.CalcTranslatedName translatedName = null;

    public EnchantmentFunctions.OnEntityDamaged onEntityDamaged = null;
    public EnchantmentFunctions.OnUserHurt onUserHurt = null;

    public final IEnchantmentDefinition thisDefinition = new MCEnchantmentDefinition(this);

    private static EntityEquipmentSlot[] calcSlotArray(IEntityEquipmentSlot[] array) {
        EntityEquipmentSlot[] out = new EntityEquipmentSlot[array.length];
        for (int indec = 0; indec < array.length; indec++) {
            out[indec] = CraftTweakerMC.getEntityEquipmentSlot(array[indec]);
        }
        return out;
    }

    protected CoTEnchantment(Rarity rarity, EnumEnchantmentType type, IEntityEquipmentSlot[] applicableEquipmentTypes) {
        super(rarity, type, calcSlotArray(applicableEquipmentTypes));
    }

    @Override
    public int getMinLevel() {
        return minLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        if (enchantabilityMin != null) {
            return enchantabilityMin.handle(thisDefinition, enchantmentLevel);
        }
        return super.getMaxEnchantability(enchantmentLevel);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        if (enchantabilityMax != null) {
            return enchantabilityMax.handle(thisDefinition, enchantmentLevel);
        }
        return super.getMaxEnchantability(enchantmentLevel);
    }

    @Override
    public int calcModifierDamage(int level, DamageSource source) {
        if (modifierDamage != null) {
            return modifierDamage.handle(thisDefinition, level, CraftTweakerMC.getIDamageSource(source));
        }
        return super.calcModifierDamage(level, source);
    }

    @Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
        if (damageByCreature != null) {
            return damageByCreature.handle(thisDefinition, level, creatureType.name());
        }
        return super.calcDamageByCreature(level, creatureType);
    }

    @Override
    public String getTranslatedName(int level) {
        if (translatedName != null) {
            return translatedName.handle(thisDefinition, level);
        }
        return super.getTranslatedName(level);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        if (apply != null) {
            return apply.handle(thisDefinition, CraftTweakerMC.getIItemStack(stack));
        }
        return super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (onEntityDamaged != null) {
            onEntityDamaged.handle(thisDefinition, CraftTweakerMC.getIEntityLivingBase(user), CraftTweakerMC.getIEntity(target), level);
        } else {
            super.onEntityDamaged(user, target, level);
        }
    }

    @Override
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
        if (onUserHurt != null) {
            onUserHurt.handle(thisDefinition, CraftTweakerMC.getIEntityLivingBase(user), CraftTweakerMC.getIEntity(attacker), level);
        } else {
            super.onUserHurt(user, attacker, level);
        }
    }

    @Override
    public boolean isTreasureEnchantment() {
        return treasure;
    }

    @Override
    public boolean isCurse() {
        return curse;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (applyAtEnchTable != null) {
            return applyAtEnchTable.handle(thisDefinition, CraftTweakerMC.getIItemStack(stack));
        }
        return super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return allowedOnBooks;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        if (canApplyTogether != null) {
            return canApplyTogether.handle(thisDefinition, new MCEnchantmentDefinition(ench));
        }
        return super.canApplyTogether(ench);
    }
}

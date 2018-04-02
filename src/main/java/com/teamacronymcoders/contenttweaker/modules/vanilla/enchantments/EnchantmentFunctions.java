package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

public final class EnchantmentFunctions {

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcEnchantability")
    public interface CalcEnchantability {
        int handle(IEnchantmentDefinition thisEnchantment, int level);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcModifierDamage")
    public interface CalcModifierDamage {
        int handle(IEnchantmentDefinition thisEnchantment, int level, IDamageSource damageSource);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcDamageByCreature")
    public interface CalcDamageByCreature {
        float handle(IEnchantmentDefinition thisEnchantment, int level, String creatureType);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcCanApplyTogether")
    public interface CalcCanApplyTogether {
        boolean handle(IEnchantmentDefinition thisEnchantment, IEnchantmentDefinition other);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcCanApply")
    public interface CalcCanApply {
        boolean handle(IEnchantmentDefinition thisEnchantment, IItemStack item);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.CalcTranslatedName")
    public interface CalcTranslatedName {
        String handle(IEnchantmentDefinition thisEnchantment, int level);
    }


    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.OnEntityDamaged")
    public interface OnEntityDamaged {
        void handle(IEnchantmentDefinition thisEnchantment, IEntityLivingBase user, IEntity target, int level);
    }

    @ZenRegister
    @ZenClass("mods.contenttweaker.enchantments.OnUserHurt")
    public interface OnUserHurt {
        void handle(IEnchantmentDefinition thisEnchantment, IEntityLivingBase user, IEntity attacker, int level);
    }
}

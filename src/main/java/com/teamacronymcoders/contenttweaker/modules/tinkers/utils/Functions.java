package com.teamacronymcoders.contenttweaker.modules.tinkers.utils;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.BlockBreakEvent;
import crafttweaker.api.event.BlockHarvestDropsEvent;
import crafttweaker.api.event.EntityLivingHurtEvent;
import crafttweaker.api.event.PlayerBreakSpeedEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

public class Functions {

    @ZenClass("mods.tconstruct.traits.Update")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnUpdate {
        void handle(IItemStack tool, IWorld world, IEntity entity, int itemSlot, boolean isSelected);
    }

    @ZenClass("mods.tconstruct.traits.MiningSpeed")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface MiningSpeed {
        void handle(IItemStack tool, PlayerBreakSpeedEvent event);
    }

    @ZenClass("mods.tconstruct.traits.BeforeBlockBreak")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface BeforeBlockBreak {
        void handle(IItemStack tool, BlockBreakEvent event);
    }

    @ZenClass("mods.tconstruct.traits.AfterBlockBreak")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface AfterBlockBreak {
        void handle(IItemStack tool, IWorld world, IBlockState blockState, IBlockPos pos, IEntityLivingBase miner, boolean wasEffective);
    }

    @ZenClass("mods.tconstruct.traits.BlockHarvestDrops")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface BlockHarvestDrops {
        void handle(IItemStack tool, BlockHarvestDropsEvent event);
    }

    @ZenClass("mods.tconstruct.traits.IsCriticalHit")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface IsCriticalHit {
        boolean handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target);
    }

    @ZenClass("mods.tconstruct.traits.Damage")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface Damage {
        float handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float originalDamage, float currentDamage, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.OnHit")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnHit {
        void handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damage, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.AfterHit")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface AfterHit {
        void handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit);
    }

    @ZenClass("mods.tconstruct.traits.KnockBack")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface KnockBack {
        float handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.OnBlock")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnBlock {
        void handle(IItemStack tool, IPlayer attacker, EntityLivingHurtEvent event);
    }

    @ZenClass("mods.tconstruct.traits.OnToolDamage")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnToolDamage {
        int handle(IItemStack tool, int damage, int newDamage, IEntityLivingBase entity);
    }


    @ZenClass("mods.tconstruct.traits.OnToolHeal")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnToolHeal {
        int handle(IItemStack tool, int damage, int newDamage, IEntityLivingBase entity);
    }

    @ZenClass("mods.tconstruct.traits.OnToolRepair")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnToolRepair {
        void handle(IItemStack tool, int amount);
    }

    @ZenClass("mods.tconstruct.traits.OnPlayerHurt")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface OnPlayerHurt {
        void handle(IItemStack tool, IPlayer player, IEntityLivingBase attacker, EntityLivingHurtEvent event);
    }

    @ZenClass("mods.tconstruct.traits.CanApplyTogetherTrait")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface CanApplyTogether {
        boolean handle(String otherTrait);
    }

    @ZenClass("mods.tconstruct.traits.ExtraInfo")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface ExtraInfo {
        String[] handle(IItemStack item, IData tool);
    }

    @ZenClass("mods.tconstruct.materials.ItemLocalizer")
    @ZenRegister
    @ModOnly("tconstruct")
    public interface ItemLocalizer {
        String handle(String itemName);
    }
}

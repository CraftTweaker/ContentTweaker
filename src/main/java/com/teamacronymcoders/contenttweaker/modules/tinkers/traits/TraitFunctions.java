package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
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

public class TraitFunctions {

    @ZenClass("mods.tconstruct.traits.Update")
    @ZenRegister
    public interface onUpdate {
        void handle(IItemStack tool, IWorld world, IEntity entity, int itemSlot, boolean isSelected);
    }

    @ZenClass("mods.tconstruct.traits.MiningSpeed")
    @ZenRegister
    public interface miningSpeed {
        void handle(IItemStack tool, PlayerBreakSpeedEvent event);
    }

    @ZenClass("mods.tconstruct.traits.BeforeBlockBreak")
    @ZenRegister
    public interface beforeBlockBreak {
        void handle(IItemStack tool, BlockBreakEvent event);
    }

    @ZenClass("mods.tconstruct.traits.AfterBlockBreak")
    @ZenRegister
    public interface afterBlockBreak {
        void handle(IItemStack tool, IWorld world, IBlockState blockState, IBlockPos pos, IEntityLivingBase miner, boolean wasEffective);
    }

    @ZenClass("mods.tconstruct.traits.BlockHarvestDrops")
    @ZenRegister
    public interface blockHarvestDrops {
        void handle(IItemStack tool, BlockHarvestDropsEvent event);
    }

    @ZenClass("mods.tconstruct.traits.IsCriticalHit")
    @ZenRegister
    public interface isCriticalHit {
        boolean handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target);
    }

    @ZenClass("mods.tconstruct.traits.Damage")
    @ZenRegister
    public interface damage {
        float handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float originalDamage, float currentDamage, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.OnHit")
    @ZenRegister
    public interface onHit {
        void handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damage, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.AfterHit")
    @ZenRegister
    public interface afterHit {
        void handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit);
    }

    @ZenClass("mods.tconstruct.traits.KnockBack")
    @ZenRegister
    public interface knockBack {
        float handle(IItemStack tool, IEntityLivingBase attacker, IEntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical);
    }

    @ZenClass("mods.tconstruct.traits.OnBlock")
    @ZenRegister
    public interface onBlock {
        void handle(IItemStack tool, IPlayer attacker, EntityLivingHurtEvent event);
    }

    @ZenClass("mods.tconstruct.traits.OnToolDamage")
    @ZenRegister
    public interface onToolDamage {
        int handle(IItemStack tool, int damage, int newDamage, IEntityLivingBase entity);
    }


    @ZenClass("mods.tconstruct.traits.OnToolHeal")
    @ZenRegister
    public interface onToolHeal {
        int handle(IItemStack tool, int damage, int newDamage, IEntityLivingBase entity);
    }

    @ZenClass("mods.tconstruct.traits.OnToolRepair")
    @ZenRegister
    public interface onToolRepair {
        void handle(IItemStack tool, int amount);
    }

    @ZenClass("mods.tconstruct.traits.OnPlayerHurt")
    @ZenRegister
    public interface onPlayerHurt {
        void handle(IItemStack tool, IPlayer player, IEntityLivingBase attacker, EntityLivingHurtEvent event);
    }

    @ZenClass("mods.tconstruct.traits.canApplyTogetherTrait")
    @ZenRegister
    public interface canApplyTogether {
        boolean handle(String otherTrait);
    }
}

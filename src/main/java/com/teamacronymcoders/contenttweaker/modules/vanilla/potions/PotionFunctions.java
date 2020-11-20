package com.teamacronymcoders.contenttweaker.modules.vanilla.potions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.potions.IPotion;
import org.jetbrains.annotations.Nullable;
import stanhebben.zenscript.annotations.ZenClass;

public class PotionFunctions {

    @ZenClass("mods.contenttweaker.potions.isReady")
    @ZenRegister
    public interface isReady {
        boolean handle(int duration, int amplifier);
    }

    @ZenClass("mods.contenttweaker.potions.performEffect")
    @ZenRegister
    public interface performEffect {
        void handle(IPotion thisPotion, IEntityLivingBase iEntityLivingBase, int amplifier);
    }

    @ZenClass("mods.contenttweaker.potions.affectEntity")
    @ZenRegister
    public interface affectEntity {
        void handle(IPotion thisPotion, @Nullable IEntity source, @Nullable IEntity indirectSource, IEntityLivingBase iEntityLivingBase, int amplifier, double health);
    }
}
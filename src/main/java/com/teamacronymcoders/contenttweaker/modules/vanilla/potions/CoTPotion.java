package com.teamacronymcoders.contenttweaker.modules.vanilla.potions;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import crafttweaker.mc1120.potions.MCPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoTPotion extends Potion {

    protected CoTPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    public final IPotion thisDefinition = new MCPotion(this);

    public boolean isInstant;

    @Override
    public boolean isInstant() {
        return isInstant;
    }

    public boolean isBeneficial;

    @Override
    public @NotNull Potion setBeneficial() {
        this.isBeneficial = true;
        return this;
    }

    public PotionFunctions.isReady isReady = null;

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (isReady != null) {
            return isReady.handle(duration, amplifier);
        } else {
            return true;
        }
    }

    public PotionFunctions.performEffect performEffect = null;

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (performEffect != null) {
            performEffect.handle(thisDefinition, CraftTweakerMC.getIEntityLivingBase(entityLivingBaseIn), amplifier);
        }
    }

    public PotionFunctions.affectEntity affectEntity = null;

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        if (affectEntity != null) {
            affectEntity.handle(thisDefinition, CraftTweakerMC.getIEntity(source), CraftTweakerMC.getIEntity(indirectSource), CraftTweakerMC.getIEntityLivingBase(entityLivingBaseIn), amplifier, health);
        }
    }
}
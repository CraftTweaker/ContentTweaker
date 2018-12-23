package com.teamacronymcoders.contenttweaker.api.ctobjects.entity;

import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player.CTPlayer;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityHelper {

    public static IEntity getIEntity(Entity entity) {
        return entity instanceof EntityPlayer
                ? new CTPlayer((EntityPlayer) entity)
                : CraftTweakerMC.getIEntity(entity);
    }

    public static IEntityLivingBase getIEntityLivingBase(EntityLivingBase entity) {
        return entity instanceof EntityPlayer
                ? new CTPlayer((EntityPlayer) entity)
                : CraftTweakerMC.getIEntityLivingBase(entity);
    }
}

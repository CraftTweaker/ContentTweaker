package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemInteractWithEntity")
@Document("mods/contenttweaker/API/functions/IItemInteractWithEntity")
public interface IItemInteractWithEntity extends ICotFunction{
    @ZenCodeType.Method
    String apply(MCItemStackMutable stack, PlayerEntity player, LivingEntity target, Hand hand);
}

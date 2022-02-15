package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUseFinish")
@Document("mods/contenttweaker/API/functions/IItemUseFinish")
public interface IItemUseFinish extends ICotFunction {
    @ZenCodeType.Method
    ItemStack apply(IItemStack stack, World worldIn, LivingEntity entityLiving);
}

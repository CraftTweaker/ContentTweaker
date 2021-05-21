package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.LivingEntity;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUsingTick")
@Document("mods/contenttweaker/API/functions/IItemUsingTick")
public interface IItemUsingTick extends ICotFunction {
    @ZenCodeType.Method
    void apply(MCItemStackMutable stack, LivingEntity player, int count);
}

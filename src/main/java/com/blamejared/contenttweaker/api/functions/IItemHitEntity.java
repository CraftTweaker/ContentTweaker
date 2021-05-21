package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.LivingEntity;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemHitEntity")
@Document("mods/contenttweaker/API/functions/IItemHitEntity")
public interface IItemHitEntity extends ICotFunction {
    @ZenCodeType.Method
    boolean apply(MCItemStackMutable stack, LivingEntity target, LivingEntity attacker);
}

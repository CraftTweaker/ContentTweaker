package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemInventoryTick")
@Document("mods/contenttweaker/API/functions/IItemInventoryTick")
public interface IItemInventoryTick extends ICotFunction {
    @ZenCodeType.Method
    void apply(MCItemStackMutable stack, World world, Entity entity, int itemSlot, boolean isSelected);
}

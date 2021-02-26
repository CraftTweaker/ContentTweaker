package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemRightClick")
@Document("mods/contenttweaker/API/functions/IItemRightClick")
public interface IItemRightClick extends ICotFunction {
    @ZenCodeType.Method
    String apply(MCItemStackMutable item, PlayerEntity playerEntity, World world, String hand);
}

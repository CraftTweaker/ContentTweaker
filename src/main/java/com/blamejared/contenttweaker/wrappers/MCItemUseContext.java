package com.blamejared.contenttweaker.wrappers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A class to handle some data when using item.
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemUseContext")
@Document("mods/contenttweaker/API/item/MCItemUseContext")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemUseContext")
public class MCItemUseContext {
    private final ItemUseContext internal;

    public MCItemUseContext(ItemUseContext internal) {
        this.internal = internal;
    }

    @ZenCodeType.Getter("player")
    @ZenCodeType.Nullable
    public PlayerEntity getPlayer() {
        return internal.getPlayer();
    }

    @ZenCodeType.Getter("pos")
    public BlockPos getPos() {
        return internal.getPos();
    }

    @ZenCodeType.Getter("face")
    public MCDirection getDirection() {
        return MCDirection.get(internal.getFace());
    }

    @ZenCodeType.Getter("item")
    public IItemStack getItem() {
        return new MCItemStack(internal.getItem());
    }

    @ZenCodeType.Getter("hasSecondaryUseForPlayer")
    public boolean hasSecondaryUseForPlayer() {
        return internal.hasSecondaryUseForPlayer();
    }

    @ZenCodeType.Getter("world")
    public World getWorld() {
        return internal.getWorld();
    }

    public ItemUseContext getInternal() {
        return internal;
    }
}

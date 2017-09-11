package com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.api.block.IBlock;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.BlockState")
public interface ICTBlockState extends ICTObject<IBlockState> {
    @ZenMethod
    IBlock getBlock();

    @ZenMethod
    int getMeta();
}

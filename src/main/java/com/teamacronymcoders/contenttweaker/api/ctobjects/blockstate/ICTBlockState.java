package com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.BlockState")
public interface ICTBlockState extends ICTObject<IBlockState> {
    @ZenMethod
    @ZenGetter("block")
    IBlock getBlock();

    @ZenMethod
    @ZenGetter("meta")
    int getMeta();
}

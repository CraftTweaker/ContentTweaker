package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockAccess;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.IBlockColorSupplier")
public interface IBlockColorSupplier {
    CTColor getColor(ICTBlockState state, IBlockAccess access, IBlockPos pos, int tintIndex);
}
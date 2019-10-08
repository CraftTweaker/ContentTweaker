package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.FaceShape;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockAccess;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.IBlockFaceSupplier")
public interface IBlockFaceSupplier {
    FaceShape getBlockFaceShape(IBlockAccess access, ICTBlockState state, IBlockPos pos, Facing facing);
}
package com.blamejared.contenttweaker.blocks.types.machine.capability;

import mcp.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

@MethodsReturnNonnullByDefault
public interface ICotCapabilityInstance {
    
    <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side);
    
    CompoundNBT createDataCompound();
    
    void deserializeDataCompound(CompoundNBT compound);
    
    void onBlockBroken(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving);
}

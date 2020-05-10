package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CoTTile extends TileEntity implements INamedContainerProvider {
    
    private final CoTCapabilityInstanceManager instanceManager;
    
    
    public CoTTile(ResourceLocation location, CoTCapabilityInstanceManager instanceManager) {
        super(MachineBlockRegistry.TILE_TYPES.get(location));
        this.instanceManager = instanceManager;
        instanceManager.registerTile(this);
    }
    
    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        final CompoundNBT data = compound.getCompound("data");
        instanceManager.deserializeDataCompound(data);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        final CompoundNBT dataCompound = instanceManager.createDataCompound();
        compound.put("data", dataCompound);
        return compound;
    }
    
    
    @Nullable
    @Override
    public CoTContainer createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return instanceManager.createMenu(windowId, playerInventory);
    }
    
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        final LazyOptional<T> capability = instanceManager.getCapability(cap, side);
        if(capability.isPresent()) {
            return capability;
        }
        
        return super.getCapability(cap, side);
    }
    
    public void onBlockBroken(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        instanceManager.onBlockBroken(state, world, pos, newState, isMoving);
    }
    
    @Override
    public ITextComponent getDisplayName() {
        final TileEntityType<?> type = getType();
        final ResourceLocation registryName = type.getRegistryName();
        final String translationKey = Util.makeTranslationKey("tile", registryName);
        return new TranslationTextComponent(translationKey);
    }
}

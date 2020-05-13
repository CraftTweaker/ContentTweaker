package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;
import java.util.*;

public class CoTCapabilityInstanceManager {
    
    private final Map<ICotCapability, ICotCapabilityInstance> configurations;
    private CoTTile tile;
    
    public CoTCapabilityInstanceManager(CoTBlockTile allBlock) {
        this.configurations = new HashMap<>();
        final CoTCapabilityConfigurationManager capabilityConfiguration = allBlock.getCapabilityConfiguration();
        capabilityConfiguration.createInstancesFor(this);
    }
    
    public void addInstance(ICotCapability capability, @Nullable ICotCapabilityInstance instance) {
        if(instance == null) {
            return;
        }
        configurations.put(capability, instance);
    }
    
    public void registerTile(CoTTile tile) {
        this.tile = tile;
    }
    
    public void markDirty() {
        if(tile != null) {
            tile.markDirty();
        }
    }
    
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        for(ICotCapabilityInstance capabilityInstance : configurations.values()) {
            final LazyOptional<T> capability = capabilityInstance.getCapability(cap, side);
            if(capability.isPresent()) {
                return capability;
            }
        }
        
        return LazyOptional.empty();
    }
    
    
    public CompoundNBT createDataCompound() {
        final CompoundNBT compound = new CompoundNBT();
        configurations.forEach((cap, capInstance) -> {
            CompoundNBT dataCompound = capInstance.createDataCompound();
            compound.put(cap.getId().toString(), dataCompound);
        });
        
        return compound;
    }
    
    public void deserializeDataCompound(CompoundNBT dataCompound) {
        for(String s : dataCompound.keySet()) {
            final ResourceLocation resourceLocation = ResourceLocation.tryCreate(s);
            final ICotCapabilityInstance instance = findInstance(resourceLocation);
            if(instance != null) {
                final CompoundNBT compound = dataCompound.getCompound(s);
                instance.deserializeDataCompound(compound);
            }
        }
    }
    
    private ICotCapabilityInstance findInstance(ResourceLocation location) {
        if(location == null) {
            return null;
        }
        for(ICotCapability iCotCapability : this.configurations.keySet()) {
            if(iCotCapability.getId().equals(location)) {
                return configurations.get(iCotCapability);
            }
        }
        return null;
    }
    
    public void onBlockBroken(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        for(ICotCapabilityInstance value : this.configurations.values()) {
            value.onBlockBroken(state, world, pos, newState, isMoving);
        }
    }
    
    public CoTContainer createMenu(int windowId, PlayerInventory playerInventory) {
        if(this.configurations.isEmpty()) {
            return null;
        }
        
        final ResourceLocation registryName = tile.getType().getRegistryName();
        final ContainerType<CoTContainer> containerType = MachineBlockRegistry.CONTAINER_TYPES.get(registryName);
        final CoTContainer coTContainer = new CoTContainer(containerType, windowId);
        
        for(ICotCapabilityInstance value : this.configurations.values()) {
            value.addToContainer(coTContainer, playerInventory);
        }
        return coTContainer;
    }
    
    public Collection<ICotCapabilityInstanceTickable> tickableTiles() {
        List<ICotCapabilityInstanceTickable> list = new ArrayList<>();
        for(ICotCapabilityInstance iCotCapabilityInstance : this.configurations.values()) {
            if(iCotCapabilityInstance instanceof ICotCapabilityInstanceTickable) {
                final ICotCapabilityInstanceTickable tickable = (ICotCapabilityInstanceTickable) iCotCapabilityInstance;
                if(tickable.needsTicking()) {
                    list.add(tickable);
                }
            }
        }
        return list;
    }
    
    public boolean requiresTickableTile() {
        for(ICotCapabilityInstance iCotCapabilityInstance : this.configurations.values()) {
            if(iCotCapabilityInstance instanceof ICotCapabilityInstanceTickable) {
                final ICotCapabilityInstanceTickable tickable = (ICotCapabilityInstanceTickable) iCotCapabilityInstance;
                if(tickable.needsTicking()) {
                    return true;
                }
            }
        }
        return false;
    }
}

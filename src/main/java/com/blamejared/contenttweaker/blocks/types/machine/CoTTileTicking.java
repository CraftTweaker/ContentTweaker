package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

import java.util.*;

public class CoTTileTicking extends CoTTile implements ITickableTileEntity {
    
    
    private final Collection<ICotCapabilityInstanceTickable> tickableTiles;
    
    public CoTTileTicking(ResourceLocation location, CoTCapabilityInstanceManager instanceManager) {
        super(location, instanceManager);
        tickableTiles = instanceManager.tickableTiles();
    }
    
    @Override
    public void tick() {
        if(world == null || world.isRemote){
            return;
        }
        boolean dirty = false;
        for(ICotCapabilityInstanceTickable tickable : tickableTiles) {
            if(tickable.tick(this)){
                dirty = true;
            }
        }
        
        if(dirty){
            markDirty();
        }
    }
}

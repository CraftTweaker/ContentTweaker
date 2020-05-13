package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;

public interface ICotCapabilityInstanceTickable extends ICotCapabilityInstance {
    boolean tick(CoTTileTicking tile);
    
    default boolean needsTicking() {
        return true;
    }
}

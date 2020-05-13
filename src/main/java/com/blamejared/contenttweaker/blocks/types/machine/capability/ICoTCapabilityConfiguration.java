package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.impl.util.*;

import javax.annotation.*;
import java.util.*;

public interface ICoTCapabilityConfiguration {
    
    @Nullable
    ICotCapabilityInstance createCapabilityInstance(CoTCapabilityInstanceManager instanceManager);
    
    void addToScreen(CoTScreen screen);
    
    Collection<WriteableResource> getResourcePackResources(MCResourceLocation blockName);
    
}

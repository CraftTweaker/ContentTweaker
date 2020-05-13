package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.*;
import net.minecraft.util.*;

public class CoTCapabilities {
    
    public static final ICotCapability ITEM = () -> new ResourceLocation(ContentTweaker.MOD_ID, "capability_item");
    public static final ICotCapability GUI = () -> new ResourceLocation(ContentTweaker.MOD_ID, "capability_gui");
    
    private CoTCapabilities() {
    }
}

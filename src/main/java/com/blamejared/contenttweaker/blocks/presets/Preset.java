package com.blamejared.contenttweaker.blocks.presets;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.Preset")
@FunctionalInterface
public interface Preset {
    @ZenCodeType.Method
    MCBlockProperties apply(MCBlockProperties properties);
}

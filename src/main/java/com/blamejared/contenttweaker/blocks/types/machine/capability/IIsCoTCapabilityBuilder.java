package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.builder.IIsCoTCapabilityBuilder")
public interface IIsCoTCapabilityBuilder {
    void applyToBuilder(BuilderMachine builderMachine);
}

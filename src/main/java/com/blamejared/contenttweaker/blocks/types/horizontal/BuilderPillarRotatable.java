package com.blamejared.contenttweaker.blocks.types.horizontal;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.custom.BuilderPillarRotatable")
public class BuilderPillarRotatable implements IIsBuilder {
    
    private final BlockBuilder builder;
    private final Function<MCResourceLocation, MCResourceLocation> end;
    private final Function<MCResourceLocation, MCResourceLocation> sides;
    
    public BuilderPillarRotatable(BlockBuilder builder) {
        this.builder = builder;
        end = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_end");
        sides = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    public MCResourceLocation getEnd(MCResourceLocation name) {
        return end.apply(name);
    }
    
    public MCResourceLocation getSides(MCResourceLocation name) {
        return sides.apply(name);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerBlock(new CoTBlockRotatablePillar(this, location));
    }
    
    public BlockBuilder getBuilder() {
        return builder;
    }
}

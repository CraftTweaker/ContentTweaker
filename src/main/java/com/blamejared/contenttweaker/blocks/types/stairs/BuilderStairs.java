package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.stairs.BuilderStairs")
@Document("mods/contenttweaker/block/stairs/BuilderStairs")
public class BuilderStairs extends BlockTypeBuilder {
    
    private final Function<MCResourceLocation, MCResourceLocation> top;
    private final Function<MCResourceLocation, MCResourceLocation> bottom;
    private final Function<MCResourceLocation, MCResourceLocation> sides;
    
    public BuilderStairs(BlockBuilder blockBuilder) {
        super(blockBuilder);
        top = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_top");
        bottom = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_bottom");
        sides = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    
    public MCResourceLocation getTop(MCResourceLocation stairsName) {
        return top.apply(stairsName);
    }
    
    public MCResourceLocation getBottom(MCResourceLocation stairsName) {
        return bottom.apply(stairsName);
    }
    
    public MCResourceLocation getSides(MCResourceLocation stairsName) {
        return sides.apply(stairsName);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerBlock(new CoTStairsBlock(this, location));
    }
}

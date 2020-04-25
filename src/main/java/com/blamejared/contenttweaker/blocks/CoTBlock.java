package com.blamejared.contenttweaker.blocks;

import net.minecraft.block.*;

public class CoTBlock extends Block {
    
    public CoTBlock(Properties properties) {
        super(properties);
    }
    
    public CoTBlock(MCBlockProperties properties) {
        this(properties.getInternal());
    }
}

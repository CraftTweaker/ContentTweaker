package com.blamejared.contenttweaker.items;

import mcp.*;
import net.minecraft.item.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoTItem extends Item {
    
    public CoTItem(MCItemRepresentation representation) {
        super(representation.getProperties().createInternal());
    }
}

package com.blamejared.contenttweaker.blocks.types.custom;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.blocks.PlaceStateMapping")
@Document("mods/contenttweaker/blocks/PlaceStateMapping")
@FunctionalInterface
public interface PlaceStateMapping {
    @ZenCodeType.Nullable MCBlockState getState(MCBlockState defaultState, MCBlockItemUseContext mcContext);
}

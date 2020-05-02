package com.blamejared.contenttweaker.blocks.presets;

import com.blamejared.contenttweaker.blocks.presets.stairs.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.Presets")
public final class Presets {
    
    private Presets() {
    }
    
    @ZenCodeType.Method
    public static Preset horizontal() {
        return new PresetHorizontal();
    }
    
    @ZenCodeType.Method
    public static Preset horizontalAll(@ZenCodeType.Nullable MCResourceLocation top, @ZenCodeType.Nullable MCResourceLocation bottom, @ZenCodeType.Nullable MCResourceLocation front, @ZenCodeType.Nullable MCResourceLocation left, @ZenCodeType.Nullable MCResourceLocation back, @ZenCodeType.Nullable MCResourceLocation right) {
        return new PresetHorizontal(top, bottom, front, left, back, right);
    }
    
    @ZenCodeType.Method
    public static Preset horizontalSides(@ZenCodeType.Nullable MCResourceLocation topBottom, @ZenCodeType.Nullable MCResourceLocation front, @ZenCodeType.Nullable MCResourceLocation left, @ZenCodeType.Nullable MCResourceLocation back, @ZenCodeType.Nullable MCResourceLocation right) {
        return new PresetHorizontal(topBottom, front, left, back, right);
    }
    
    @ZenCodeType.Method
    public static Preset horizontal(@ZenCodeType.Nullable MCResourceLocation topBottom, @ZenCodeType.Nullable MCResourceLocation sides) {
        return new PresetHorizontal(topBottom, sides);
    }
    
    @ZenCodeType.Method
    public static Preset horizontalTopBottomSides(@ZenCodeType.Nullable MCResourceLocation top, @ZenCodeType.Nullable MCResourceLocation bottom, @ZenCodeType.Nullable MCResourceLocation sides) {
        return new PresetHorizontal(top, bottom, sides);
    }
    
    @ZenCodeType.Method
    public static Preset stairs() {
        return new PresetStairs();
    }
    
    @ZenCodeType.Method
    public static Preset stairsTopBottomSides(@ZenCodeType.Nullable MCResourceLocation top, @ZenCodeType.Nullable MCResourceLocation bottom, @ZenCodeType.Nullable MCResourceLocation sides) {
        return new PresetStairs(top, bottom, sides);
    }
}

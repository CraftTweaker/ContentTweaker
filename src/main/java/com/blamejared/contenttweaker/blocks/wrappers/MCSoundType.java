package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCSoundType")
@Document("mods/contenttweaker/block/MCSoundType")
@ZenWrapper(wrappedClass = "net.minecraft.block.SoundType", creationMethodFormat = "new MCSoundType(%s, \"unknown_wrapped\")",displayStringFormat = "%s.getCommandString()")
public class MCSoundType implements CommandStringDisplayable {
    
    private final SoundType internal;
    private final String name;
    
    public MCSoundType(SoundType internal, String name) {
        this.internal = internal;
        this.name = name.toLowerCase();
    }
    
    public SoundType getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("volume")
    public float getVolume() {
        return internal.getVolume();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pitch")
    public float getPitch() {
        return internal.getPitch();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public String getName() {
        return name;
    }
    
    @Override
    public String getCommandString() {
        return "<soundtype:" + name + ">";
    }
    
    public static MCSoundType getFromString(String uppercase) {
        switch(uppercase) {
            case "WOOD":
                return new MCSoundType(SoundType.WOOD, "WOOD");
            case "GROUND":
                return new MCSoundType(SoundType.GROUND, "GROUND");
            case "PLANT":
                return new MCSoundType(SoundType.PLANT, "PLANT");
            case "STONE":
                return new MCSoundType(SoundType.STONE, "STONE");
            case "METAL":
                return new MCSoundType(SoundType.METAL, "METAL");
            case "GLASS":
                return new MCSoundType(SoundType.GLASS, "GLASS");
            case "CLOTH":
                return new MCSoundType(SoundType.CLOTH, "CLOTH");
            case "SAND":
                return new MCSoundType(SoundType.SAND, "SAND");
            case "SNOW":
                return new MCSoundType(SoundType.SNOW, "SNOW");
            case "LADDER":
                return new MCSoundType(SoundType.LADDER, "LADDER");
            case "ANVIL":
                return new MCSoundType(SoundType.ANVIL, "ANVIL");
            case "SLIME":
                return new MCSoundType(SoundType.SLIME, "SLIME");
            case "WET_GRASS":
                return new MCSoundType(SoundType.WET_GRASS, "WET_GRASS");
            case "CORAL":
                return new MCSoundType(SoundType.CORAL, "CORAL");
            case "BAMBOO":
                return new MCSoundType(SoundType.BAMBOO, "BAMBOO");
            case "BAMBOO_SAPLING":
                return new MCSoundType(SoundType.BAMBOO_SAPLING, "BAMBOO_SAPLING");
            case "SCAFFOLDING":
                return new MCSoundType(SoundType.SCAFFOLDING, "SCAFFOLDING");
            case "SWEET_BERRY_BUSH":
                return new MCSoundType(SoundType.SWEET_BERRY_BUSH, "SWEET_BERRY_BUSH");
            case "CROP":
                return new MCSoundType(SoundType.CROP, "CROP");
            case "STEM":
                return new MCSoundType(SoundType.STEM, "STEM");
            case "NETHER_WART":
                return new MCSoundType(SoundType.NETHER_WART, "NETHER_WART");
            case "LANTERN":
                return new MCSoundType(SoundType.LANTERN, "LANTERN");
        }
        return null;
    }
}

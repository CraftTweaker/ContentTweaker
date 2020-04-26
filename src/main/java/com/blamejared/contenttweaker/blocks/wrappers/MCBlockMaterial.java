package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.material.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCBlockMaterial")
@Document("mods/contenttweaker/block/MCBlockMaterial")
@ZenWrapper(wrappedClass = "new.minecraft.block.Block$Properties", displayStringFormat = "%s.getInternal()")
public class MCBlockMaterial implements CommandStringDisplayable {
    
    private final Material internal;
    private final String name;
    
    public MCBlockMaterial(Material internal, String name) {
        this.internal = internal;
        this.name = name.toLowerCase();
    }
    
    public static MCBlockMaterial getFromString(String toUpperCase) {
        switch(toUpperCase) {
            case "AIR":
                return new MCBlockMaterial(Material.AIR, "AIR");
            case "STRUCTURE_VOID":
                return new MCBlockMaterial(Material.STRUCTURE_VOID, "STRUCTURE_VOID");
            case "PORTAL":
                return new MCBlockMaterial(Material.PORTAL, "PORTAL");
            case "CARPET":
                return new MCBlockMaterial(Material.CARPET, "CARPET");
            case "PLANTS":
                return new MCBlockMaterial(Material.PLANTS, "PLANTS");
            case "OCEAN_PLANT":
                return new MCBlockMaterial(Material.OCEAN_PLANT, "OCEAN_PLANT");
            case "TALL_PLANTS":
                return new MCBlockMaterial(Material.TALL_PLANTS, "TALL_PLANTS");
            case "SEA_GRASS":
                return new MCBlockMaterial(Material.SEA_GRASS, "SEA_GRASS");
            case "WATER":
                return new MCBlockMaterial(Material.WATER, "WATER");
            case "BUBBLE_COLUMN":
                return new MCBlockMaterial(Material.BUBBLE_COLUMN, "BUBBLE_COLUMN");
            case "LAVA":
                return new MCBlockMaterial(Material.LAVA, "LAVA");
            case "SNOW":
                return new MCBlockMaterial(Material.SNOW, "SNOW");
            case "FIRE":
                return new MCBlockMaterial(Material.FIRE, "FIRE");
            case "MISCELLANEOUS":
                return new MCBlockMaterial(Material.MISCELLANEOUS, "MISCELLANEOUS");
            case "WEB":
                return new MCBlockMaterial(Material.WEB, "WEB");
            case "REDSTONE_LIGHT":
                return new MCBlockMaterial(Material.REDSTONE_LIGHT, "REDSTONE_LIGHT");
            case "CLAY":
                return new MCBlockMaterial(Material.CLAY, "CLAY");
            case "EARTH":
                return new MCBlockMaterial(Material.EARTH, "EARTH");
            case "ORGANIC":
                return new MCBlockMaterial(Material.ORGANIC, "ORGANIC");
            case "PACKED_ICE":
                return new MCBlockMaterial(Material.PACKED_ICE, "PACKED_ICE");
            case "SAND":
                return new MCBlockMaterial(Material.SAND, "SAND");
            case "SPONGE":
                return new MCBlockMaterial(Material.SPONGE, "SPONGE");
            case "SHULKER":
                return new MCBlockMaterial(Material.SHULKER, "SHULKER");
            case "WOOD":
                return new MCBlockMaterial(Material.WOOD, "WOOD");
            case "BAMBOO_SAPLING":
                return new MCBlockMaterial(Material.BAMBOO_SAPLING, "BAMBOO_SAPLING");
            case "BAMBOO":
                return new MCBlockMaterial(Material.BAMBOO, "BAMBOO");
            case "WOOL":
                return new MCBlockMaterial(Material.WOOL, "WOOL");
            case "TNT":
                return new MCBlockMaterial(Material.TNT, "TNT");
            case "LEAVES":
                return new MCBlockMaterial(Material.LEAVES, "LEAVES");
            case "GLASS":
                return new MCBlockMaterial(Material.GLASS, "GLASS");
            case "ICE":
                return new MCBlockMaterial(Material.ICE, "ICE");
            case "CACTUS":
                return new MCBlockMaterial(Material.CACTUS, "CACTUS");
            case "ROCK":
                return new MCBlockMaterial(Material.ROCK, "ROCK");
            case "IRON":
                return new MCBlockMaterial(Material.IRON, "IRON");
            case "SNOW_BLOCK":
                return new MCBlockMaterial(Material.SNOW_BLOCK, "SNOW_BLOCK");
            case "ANVIL":
                return new MCBlockMaterial(Material.ANVIL, "ANVIL");
            case "BARRIER":
                return new MCBlockMaterial(Material.BARRIER, "BARRIER");
            case "PISTON":
                return new MCBlockMaterial(Material.PISTON, "PISTON");
            case "CORAL":
                return new MCBlockMaterial(Material.CORAL, "CORAL");
            case "GOURD":
                return new MCBlockMaterial(Material.GOURD, "GOURD");
            case "DRAGON_EGG":
                return new MCBlockMaterial(Material.DRAGON_EGG, "DRAGON_EGG");
            case "CAKE":
                return new MCBlockMaterial(Material.CAKE, "CAKE");
        }
        return null;
    }
    
    public Material getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("liquid")
    public boolean isLiquid() {
        return internal.isLiquid();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("solid")
    public boolean isSolid() {
        return internal.isSolid();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ocksMovement")
    public boolean blocksMovement() {
        return internal.blocksMovement();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("flammable")
    public boolean isFlammable() {
        return internal.isFlammable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("replaceable")
    public boolean isReplaceable() {
        return internal.isReplaceable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("opaque")
    public boolean isOpaque() {
        return internal.isOpaque();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toolNotRequired")
    public boolean isToolNotRequired() {
        return internal.isToolNotRequired();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public String getName() {
        return name;
    }
    
    @Override
    public String getCommandString() {
        return "<blockmaterial:" + getName() + ">";
    }
}

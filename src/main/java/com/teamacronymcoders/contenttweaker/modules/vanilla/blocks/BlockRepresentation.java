package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.api.json.JsonRequired;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRepresentation {

    @JsonRequired
    private String unlocalizedName;
    private CreativeTabs creativeTabs;
    private Boolean fullBlock;
    private Integer lightOpacity;
    private Boolean translucent;
    private Integer lightValue;
    private Boolean useNeighborBrightness;
    private Float blockHardness;
    private Float blockResistance;
    private Boolean enableStats;
    private Boolean needsRandomTick;
    private SoundType blockSoundType;
    private Float blockParticleGravity;
    @JsonRequired
    private Material blockMaterial;
    private Float slipperiness;

}

package com.teamacronymcoders.contenttweaker.api.ctobjects.biome;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.api.world.IBiome;
import net.minecraft.world.biome.Biome;

public interface ICTBiome extends IBiome, ICTObject<Biome> {
    float getTemperature();

    float getRainfall();

    boolean canRain();

    boolean canSnow();

    float getHeightVariance();
}

package com.teamacronymcoders.tailoredobjects.api;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;
import com.teamacronymcoders.tailoredobjects.api.utils.ResourceList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TailoredObjectsAPI {
    private static TailoredObjectsAPI instance;
    private DeserializerRegistry deserializerRegistry;
    private ResourceList<Material> materialResourceList;
    private ResourceList<SoundType> soundTypeResourceList;

    private TailoredObjectsAPI() {
        this.deserializerRegistry = new DeserializerRegistry();
        this.materialResourceList = new ResourceList<>(Material.class, Material.class);
        this.soundTypeResourceList = new ResourceList<>(SoundType.class, SoundType.class);
    }

    public static TailoredObjectsAPI getInstance() {
        if(instance == null) {
            instance = new TailoredObjectsAPI();
        }
        return instance;
    }

    public DeserializerRegistry getDeserializerRegistry() {
        return this.deserializerRegistry;
    }

    public ResourceList<Material> getBlockMaterials() {
        return this.materialResourceList;
    }

    public ResourceList<SoundType> getSoundTypes() {
        return this.soundTypeResourceList;
    }
}

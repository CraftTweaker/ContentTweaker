package com.teamacronymcoders.tailoredobjects.api;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;
import com.teamacronymcoders.tailoredobjects.api.utils.CreativeTabsResourceList;
import com.teamacronymcoders.tailoredobjects.api.utils.ResourceList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TailoredObjectsAPI {
    private static TailoredObjectsAPI instance;
    private DeserializerRegistry deserializerRegistry;
    private ResourceList<Material> materialResourceList;
    private ResourceList<SoundType> soundTypeResourceList;
    private ResourceList<CreativeTabs> creativeTabsResourceList;

    private TailoredObjectsAPI() {
        this.deserializerRegistry = new DeserializerRegistry();
        this.materialResourceList = new ResourceList<>(Material.class);
        this.soundTypeResourceList = new ResourceList<>(SoundType.class);
        this.creativeTabsResourceList = new CreativeTabsResourceList();
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

    public ResourceList<CreativeTabs> getCreativeTabs() {
        return this.creativeTabsResourceList;
    }
}

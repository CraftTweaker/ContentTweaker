package com.teamacronymcoders.contenttweaker.api;

import com.teamacronymcoders.contenttweaker.api.utils.CreativeTabsResourceList;
import com.teamacronymcoders.contenttweaker.api.utils.ResourceList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ContentTweakerAPI {
    private static ContentTweakerAPI instance;

    private IModWrapper modWrapper;
    private ResourceList<Material> materialResourceList;
    private ResourceList<SoundType> soundTypeResourceList;
    private ResourceList<CreativeTabs> creativeTabsResourceList;

    public ContentTweakerAPI(IModWrapper modWrapper) {
        this.modWrapper = modWrapper;
        this.materialResourceList = new ResourceList<>(Material.class);
        this.soundTypeResourceList = new ResourceList<>(SoundType.class);
        this.creativeTabsResourceList = new CreativeTabsResourceList();
    }

    //ONLY TO BE CALLED BY TAILORED OBJECTS
    public static void setInstance(ContentTweakerAPI contentTweakerAPI) {
        if (instance == null) {
            instance = contentTweakerAPI;
        } else {
            instance.modWrapper.logError("Some other mod tried to set api instance", null);
        }
    }

    public static ContentTweakerAPI getInstance() {
        return instance;
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

    public IModWrapper getModWrapper() {
        return this.modWrapper;
    }
}

package com.teamacronymcoders.contenttweaker.api.ctobjects.resourcelocation;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.ResourceLocation")
public class CTResourceLocation implements ICTObject<ResourceLocation> {
    private ResourceLocation resourceLocation;

    private CTResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @ZenMethod
    public static CTResourceLocation create(String resourceLocation) {
        return new CTResourceLocation(new ResourceLocation(resourceLocation));
    }

    @ZenGetter("domain")
    public String getDomain() {
        return resourceLocation.getNamespace();
    }

    @ZenGetter("path")
    public String getPath() {
        return resourceLocation.getPath();
    }

    @Override
    public ResourceLocation getInternal() {
        return resourceLocation;
    }
}

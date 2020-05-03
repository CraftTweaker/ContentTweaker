package com.blamejared.contenttweaker.items.types.basic;

import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

public class CoTItemBasic extends Item implements IIsCotItem {
    
    public CoTItemBasic(Properties properties, ResourceLocation location) {
        super(properties);
        this.setRegistryName(location);
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final List<WriteableResource> out = new ArrayList<>();
        out.add(new WriteableResourceImage(ImageType.ITEM, location));
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "models", "item").withContent(String
                .format("{\n    \"parent\" : \"item/generated\",\n    \"textures\" : \n        {\"layer0\" : \"%s:item/%s\"\n    }\n}", location
                        .getNamespace(), location.getPath())));
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }
}

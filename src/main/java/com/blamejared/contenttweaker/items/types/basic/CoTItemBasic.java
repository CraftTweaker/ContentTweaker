package com.blamejared.contenttweaker.items.types.basic;

import com.blamejared.contenttweaker.*;
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
        out.add(WriteableResourceImage.noImage(ImageType.ITEM, location));
        
        final WriteableResourceTemplate modelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "item")
                .withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_basic"))
                .setLocationProperty(location);
        out.add(modelTemplate);
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }
}

package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;

import javax.annotation.*;
import java.util.*;

public class CoTBlockItem extends BlockItem implements IIsCotItem {
    
    public CoTBlockItem(IIsCoTBlock blockIn, Item.Properties builder) {
        super(blockIn.getBlock(), builder);
        this.setRegistryName(blockIn.getMCResourceLocation().getInternal());
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final List<WriteableResource> out = new ArrayList<>();
        out.add(new WriteableResourceImage(ImageType.ITEM, location));
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "models","item").withContent(String
                .format("{\n    \"parent\" : \"%s:block/%s\"\n}", location.getNamespace(), location.getPath())));
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }
    
}

package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

final class CoTBlockBasic extends Block implements IIsCoTBlock {
    
    private final IIsCotItem item;
    
    public CoTBlockBasic(Properties properties, Item.Properties itemProperties, ResourceLocation location) {
        super(properties);
        this.setRegistryName(location);
        item = new CoTBlockItem(this, itemProperties);
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final Collection<WriteableResource> out = new ArrayList<>();
        out.add(new WriteableResourceImage(ImageType.BLOCK, location));
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "models", "block")
                .withContent("{\n" + "    \"parent\": \"block/cube_all\",\n" + "    \"textures\": {\n" + "        \"all\": \"%s:block/%s\"\n" + "    }\n" + "}\n", location
                        .getNamespace(), location.getPath()));
        
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "blockstates")
                .withContent("{\n" + "    \"variants\": {\n" + "        \"\": {\"model\" : \"%s:block/%s\"}\n" + "    }\n" + "}\n", location
                        .getNamespace(), location.getPath()));
        
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        final Collection<WriteableResource> out = new ArrayList<>();
        if(getLootTable() != LootTables.EMPTY) {
            out.add(new WriteableResourceLootTableItem(getMCResourceLocation()));
        }
        return out;
    }
}

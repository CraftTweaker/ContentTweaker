package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.functions.IItemRightClick;
import com.blamejared.contenttweaker.api.functions.IItemUse;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

public class CoTBlockItem extends BlockItem implements IIsCotItem {
    
    public CoTBlockItem(IIsCoTBlock blockIn, Item.Properties builder) {
        super(blockIn.getBlock(), builder);
        this.setRegistryName(blockIn.getRegistryNameNonNull());
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final ResourceLocation location = getRegistryNameNonNull();
        final List<WriteableResource> out = new ArrayList<>();
        out.add(WriteableResourceImage.noImage(ImageType.ITEM, location));
        final WriteableResourceTemplate modelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "item").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_block")).setLocationProperty(location);
        out.add(modelTemplate);
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }

    @Override
    public IIsCotItem setOnItemUse(IItemUse func) {
        throw new UnsupportedOperationException("onItemUse is not available for Block Items");
    }

    @Override
    public IIsCotItem setOnItemRightClick(IItemRightClick func) {
        throw new UnsupportedOperationException("onItemRightClick is not available for Block Items");
    }
}

package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.*;
import java.util.*;

public class CoTBlockItem extends BlockItem implements IIsCotItem {
    
    public CoTBlockItem(IIsCoTBlock blockIn, Item.Properties builder) {
        super(blockIn.getBlock(), builder);
        this.setRegistryName(blockIn.getRegistryNameNonNull());
    }

    public IItemInventoryTick itemInventoryTick;
    
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
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (itemInventoryTick == null) {
            super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        } else {
            itemInventoryTick.apply(new MCItemStackMutable(stack), worldIn, entityIn, itemSlot, isSelected);
        }
    }
}

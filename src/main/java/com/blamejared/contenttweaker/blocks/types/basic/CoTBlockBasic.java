package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.CoTBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public class CoTBlockBasic extends Block implements IIsCoTBlock {

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
        final ResourceLocation location = getRegistryNameNonNull();
        final Collection<WriteableResource> out = new ArrayList<>();

        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, location));

        final WriteableResourceTemplate modelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_basic")).setLocationProperty(location);
        out.add(modelTemplate);

        final WriteableResourceTemplate blockstateTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "blockstates").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "blockstates/block_basic")).setLocationProperty(location);
        out.add(blockstateTemplate);

        return out;
    }

    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        final Collection<WriteableResource> out = new ArrayList<>();
        if (getLootTable() != LootTables.EMPTY) {
            out.add(new WriteableResourceLootTableItem(getRegistryName()));
        }
        return out;
    }
}

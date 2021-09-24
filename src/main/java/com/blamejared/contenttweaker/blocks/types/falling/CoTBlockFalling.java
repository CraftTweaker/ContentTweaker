package com.blamejared.contenttweaker.blocks.types.falling;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.functions.IFallingBlockDustColorSupplier;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.ImageType;
import com.blamejared.contenttweaker.api.resources.ResourceType;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.contenttweaker.api.resources.WriteableResourceImage;
import com.blamejared.contenttweaker.api.resources.WriteableResourceLootTableItem;
import com.blamejared.contenttweaker.api.resources.WriteableResourceTemplate;
import com.blamejared.contenttweaker.blocks.CoTBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public class CoTBlockFalling extends FallingBlock implements IIsCoTBlock {

    private final IIsCotItem item;
    private final IFallingBlockDustColorSupplier dustFunc;

    public CoTBlockFalling(Properties properties, Item.Properties itemProperties, ResourceLocation location, IFallingBlockDustColorSupplier dustFunc) {
        super(properties);
        this.setRegistryName(location);
        item = new CoTBlockItem(this, itemProperties);
        this.dustFunc = dustFunc;
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
    
    @Override
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        if(reader instanceof World){
    
            return dustFunc.apply(this, state, (World)reader,pos);
        }
        return 0xFF000000;
    }
}

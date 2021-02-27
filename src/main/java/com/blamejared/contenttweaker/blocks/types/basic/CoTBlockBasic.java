package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.CoTBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@SuppressWarnings("deprecation")
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
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        VanillaFactory.REGISTRY.getFunction(this, IBlockAdded.class)
                .map(iBlockAdded -> {
                    iBlockAdded.apply(state, worldIn, pos, oldState, isMoving);
                    return 0;
                })
                .orElseGet(() -> {
                    super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
                    return 0;
                });
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return VanillaFactory.REGISTRY.getFunction(this, IBlockActivated.class)
                .map(iBlockActivated -> ActionResultType.valueOf(iBlockActivated.apply(state, worldIn, pos, player, handIn.name())))
                .orElseGet(() -> super.onBlockActivated(state, worldIn, pos, player, handIn, hit));
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        VanillaFactory.REGISTRY.getFunction(this, IBlockNeighborChanged.class)
                .map(iBlockNeighborChanged -> {
                    iBlockNeighborChanged.apply(state, worldIn, pos, blockIn, fromPos, isMoving);
                    return 0;
                })
                .orElseGet(() -> {
                    super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
                    return 0;
                });
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        VanillaFactory.REGISTRY.getFunction(this, IBlockRandomTick.class)
                .map(iBlockRandomTick -> {
                    iBlockRandomTick.apply(state, worldIn, pos, random);
                    return 0;
                })
                .orElseGet(() -> {
                    super.randomTick(state, worldIn, pos, random);
                    return 0;
                });
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        VanillaFactory.REGISTRY.getFunction(this, IBlockReplaced.class)
                .map(iBlockReplaced -> {
                    iBlockReplaced.apply(state, worldIn, pos, newState, isMoving);
                    return 0;
                })
                .orElseGet(() -> {
                    super.onReplaced(state, worldIn, pos, newState, isMoving);
                    return 0;
                });
    }
}

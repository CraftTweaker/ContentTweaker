package com.blamejared.contenttweaker.blocks.types.slab;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class CoTBlockSlab extends SlabBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final ResourceLocation sides;
    private final ResourceLocation top;
    private final ResourceLocation bottom;
    private boolean allowTinted;
    
    public CoTBlockSlab(BlockBuilderSlab blockBuilderSlab, ResourceLocation location) {
        super(blockBuilderSlab.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location);
        
        this.item = new CoTBlockItem(this, blockBuilderSlab.getBlockBuilder().getItemProperties());
        this.top = blockBuilderSlab.getTop(location);
        this.sides = blockBuilderSlab.getSides(location);
        this.bottom = blockBuilderSlab.getBottom(location);
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
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, top));
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, bottom));
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, sides));
        
        
        final WriteableResourceTemplate blockStateTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "blockstates").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "blockstates/block_slab")).setLocationProperty(location);
        out.add(blockStateTemplate);
        
        final WriteableResourceTemplate modelBottomTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "slab");
        out.add(modelBottomTemplate);
        
        final WriteableResourceTemplate modelTopTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location.getNamespace(), location.getPath() + "_top", "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "slab_top");
        out.add(modelTopTemplate);
        
        final WriteableResourceTemplate modelDoubleTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location.getNamespace(), location.getPath() + "_double", "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "cube_bottom_top");
        out.add(modelDoubleTemplate);
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getRegistryName(), "slab"));
    }

    @Override
    public boolean allowTinted() {
        return allowTinted;
    }

    @Override
    public void setAllowTinted() {
        allowTinted = true;
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
                .map(iBlockActivated -> ActionResultType.valueOf(iBlockActivated.apply(state, worldIn, pos, player, handIn)))
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

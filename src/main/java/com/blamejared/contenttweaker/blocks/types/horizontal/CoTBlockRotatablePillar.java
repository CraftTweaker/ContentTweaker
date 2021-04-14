package com.blamejared.contenttweaker.blocks.types.horizontal;

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

final class CoTBlockRotatablePillar extends RotatedPillarBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final ResourceLocation end;
    private final ResourceLocation sides;
    private boolean allowTinted;
    
    public CoTBlockRotatablePillar(BlockBuilderPillarRotatable blockBuilderPillarRotatable, ResourceLocation location) {
        super(blockBuilderPillarRotatable.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location);
        item = new CoTBlockItem(this, blockBuilderPillarRotatable.getBlockBuilder().getItemProperties());
        end = blockBuilderPillarRotatable.getEnd(location);
        sides = blockBuilderPillarRotatable.getSides(location);
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
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, end));
        if(!end.equals(sides)) {
            out.add(WriteableResourceImage.noImage(ImageType.BLOCK, sides));
        }
        
        final WriteableResourceTemplate blockModelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_rotatable_pillar")).setLocationProperty(end, "END").setLocationProperty(sides, "SIDE");
        out.add(blockModelTemplate);
        
        final WriteableResourceTemplate blockStateTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "blockstates").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "blockstates/block_rotatable_pillar")).setLocationProperty(location);
        out.add(blockStateTemplate);
        return out;
    }

    @Override
    public boolean allowTinted() {
        return allowTinted;
    }

    @Override
    public void setAllowTinted() {
        allowTinted = true;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getRegistryName()));
    }

    @Override
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return VanillaFactory.REGISTRY.getFunction(this, IBlockActivated.class)
                .map(iBlockActivated -> ActionResultType.valueOf(iBlockActivated.apply(state, worldIn, pos, player, handIn)))
                .orElseGet(() -> super.onBlockActivated(state, worldIn, pos, player, handIn, hit));
    }

    @Override
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
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

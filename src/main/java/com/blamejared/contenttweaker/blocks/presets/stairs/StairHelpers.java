package com.blamejared.contenttweaker.blocks.presets.stairs;

import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.state.properties.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.*;

public class StairHelpers {
    
    protected static final VoxelShape[] SLAB_TOP_SHAPES;
    protected static final VoxelShape[] SLAB_BOTTOM_SHAPES;
    private static final int[] field_196522_K = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    static {
        VoxelShape[] top = null;
        VoxelShape[] bottom = null;
        
        try {
            top = (VoxelShape[]) ObfuscationReflectionHelper.findField(StairsBlock.class, "SLAB_TOP_SHAPES")
                    .get(null);
            bottom = (VoxelShape[]) ObfuscationReflectionHelper.findField(StairsBlock.class, "SLAB_BOTTOM_SHAPES")
                    .get(null);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        SLAB_TOP_SHAPES = top;
        SLAB_BOTTOM_SHAPES = bottom;
    }
    
    /**
     * Disclaimer: Shamelessly stolen from MC as adding an AT to this workspace will break it
     * <p>
     * Returns a stair shape property based on the surrounding stairs from the given blockstate and position
     */
    static StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Direction direction = state.get(StairsBlock.FACING);
        BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
        if(isStairs(blockstate) && state.get(StairsBlock.HALF) == blockstate.get(StairsBlock.HALF)) {
            Direction direction1 = blockstate.get(StairsBlock.FACING);
            if(direction1.getAxis() != state.get(StairsBlock.FACING)
                    .getAxis() && isDifferentStairs(state, worldIn, pos, direction1.getOpposite())) {
                if(direction1 == direction.rotateYCCW()) {
                    return StairsShape.OUTER_LEFT;
                }
                
                return StairsShape.OUTER_RIGHT;
            }
        }
        
        BlockState blockstate1 = worldIn.getBlockState(pos.offset(direction.getOpposite()));
        if(isStairs(blockstate1) && state.get(StairsBlock.HALF) == blockstate1.get(StairsBlock.HALF)) {
            Direction direction2 = blockstate1.get(StairsBlock.FACING);
            if(direction2.getAxis() != state.get(StairsBlock.FACING)
                    .getAxis() && isDifferentStairs(state, worldIn, pos, direction2)) {
                if(direction2 == direction.rotateYCCW()) {
                    return StairsShape.INNER_LEFT;
                }
                
                return StairsShape.INNER_RIGHT;
            }
        }
        
        return StairsShape.STRAIGHT;
    }
    
    private static boolean isDifferentStairs(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        BlockState blockstate = worldIn.getBlockState(pos.offset(face));
        return !isStairs(blockstate) || blockstate.get(StairsBlock.FACING) != state.get(StairsBlock.FACING) || blockstate
                .get(StairsBlock.HALF) != state.get(StairsBlock.HALF);
    }
    
    private static boolean isStairs(BlockState state) {
        final Block block = state.getBlock();
        if(block instanceof StairsBlock) {
            return true;
        }
        final ResourceLocation registryName = block.getRegistryName();
        return registryName != null && PresetStairs.knownStairTypes.contains(new MCResourceLocation(registryName));
    }
    
    public static MCVoxelShape getShape(MCBlockState state, MCBlockPos pos) {
        final BlockState blockState = state.getInternal();
        final Half half = blockState.get(BlockStateProperties.HALF);
        final StairsShape shape = blockState.get(BlockStateProperties.STAIRS_SHAPE);
        final int horizontalIndex = blockState.get(BlockStateProperties.HORIZONTAL_FACING)
                .getHorizontalIndex();
        
        
        return new MCVoxelShape((half == Half.TOP ? SLAB_TOP_SHAPES : SLAB_BOTTOM_SHAPES)[field_196522_K[shape
                .ordinal() * 4 + horizontalIndex]]);
    }
}

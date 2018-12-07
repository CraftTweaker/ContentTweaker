package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.MCBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.MCBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.api.ctobjects.itemlist.CTItemList;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.MCWorld;
import com.teamacronymcoders.contenttweaker.api.utils.CTUtils;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity.TileEntityContent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.world.MCBlockAccess;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class BlockContent extends BlockBase {
    private BlockRepresentation blockRepresentation;

    private EnumBlockRenderType enumBlockRenderType;
    private CreativeTabs creativeTab;
    private BlockRenderLayer blockRenderLayer;
    private AxisAlignedBB blockShape;

    public BlockContent(BlockRepresentation blockRepresentation) {
        super(blockRepresentation.getBlockMaterial().getInternal(), blockRepresentation.getUnlocalizedName());
        this.blockRepresentation = blockRepresentation;
        setFields();
    }

    public void setFields() {
        this.setTranslationKey(this.blockRepresentation.getUnlocalizedName());
        if (this.blockRepresentation.getCreativeTab() != null) {
            Object creativeTab = this.blockRepresentation.getCreativeTab().getInternal();
            if (creativeTab instanceof CreativeTabs) {
                this.setCreativeTab((CreativeTabs) this.blockRepresentation.getCreativeTab().getInternal());
            }
        }
        this.setLightOpacity(this.blockRepresentation.getLightOpacity());
        this.setLightLevel(this.blockRepresentation.getLightValue());
        this.setHardness(this.blockRepresentation.getBlockHardness());
        this.setResistance(this.blockRepresentation.getBlockResistance());
        this.setHarvestLevel(this.blockRepresentation.getToolClass(), this.blockRepresentation.getToolLevel());
        this.setSoundType(this.blockRepresentation.getBlockSoundType().getInternal());
        this.enumBlockRenderType = CTUtils.getEnum(this.blockRepresentation.getEnumBlockRenderType(), EnumBlockRenderType.class);
        this.blockRenderLayer = CTUtils.getEnum(this.blockRepresentation.getBlockLayer(), BlockRenderLayer.class);
        this.blockShape = this.blockRepresentation.getAxisAlignedBB().getInternal();
        if (this.blockRepresentation.getOnRandomTick() != null) {
            this.setTickRandomly(true);
        }
        this.fullBlock = blockRepresentation.isFullBlock();
        this.translucent = blockRepresentation.isTranslucent();
    }

    @Override
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return this.blockRepresentation.isFullBlock();
    }

    @Override
    @Nonnull
    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    @Override
    @Nonnull
    public Block setCreativeTab(@Nonnull CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    @Override
    public float getEnchantPowerBonus(@Nonnull World world, @Nonnull BlockPos pos) {
        return this.blockRepresentation.getEnchantPowerBonus();
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state) {
        return this.enumBlockRenderType;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return this.blockRenderLayer;
    }

    @Override
    public void onBlockAdded(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.onBlockAdded(world, pos, state);
        activateBlockAction(this.blockRepresentation.getOnBlockPlace(), world, pos, state);
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        activateBlockAction(this.blockRepresentation.getOnBlockBreak(), world, pos, state);
        super.breakBlock(world, pos, state);

    }

    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return this.blockRepresentation.getSlipperiness();
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.blockShape;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        activateBlockAction(this.blockRepresentation.getOnRandomTick(), world, pos, state);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        activateBlockAction(this.blockRepresentation.getOnUpdateTick(), world, pos, state);
    }

    public void activateBlockAction(IBlockAction blockAction, World world, BlockPos blockPos, IBlockState blockState) {
        if (blockAction != null) {
            blockAction.onBlockAction(new MCWorld(world), new MCBlockPos(blockPos), new MCBlockState(blockState));
        }
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) {
        return Optional.ofNullable(blockRepresentation.getMobilityFlag())
                .map(PushReaction::getInternal)
                .orElse(state.getPushReaction());
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return this.blockRepresentation.isPassable();
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return this.blockRepresentation.isReplaceable();
    }

    @Override
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
        if (this.blockRepresentation.isPassable()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
        } else {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(world, pos));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return this.blockRepresentation.canEntitySpawn();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return fullBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return fullBlock;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return fullBlock;
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        if (this.blockRepresentation.getDropHandler() != null) {
            crafttweaker.api.world.IBlockAccess blockAccess = world instanceof World ? new MCWorld((World) world) : new MCBlockAccess(world);
            this.blockRepresentation.getDropHandler().handleDrops(new CTItemList(drops), blockAccess, new MCBlockPos(pos), new MCBlockState(state), fortune);
        }
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return this.blockRepresentation.beaconBase;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return blockRepresentation.tileEntityRepresentation != null;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state) {
        return blockRepresentation.tileEntityRepresentation != null ?
                new TileEntityContent(blockRepresentation.tileEntityRepresentation) : null;
    }
}

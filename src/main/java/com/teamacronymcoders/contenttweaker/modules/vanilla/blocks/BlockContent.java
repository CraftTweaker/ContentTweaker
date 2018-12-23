package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.blocks.IHasBlockColor;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.GeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.ModelType;
import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.MCBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.MCBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.api.ctobjects.itemlist.CTItemList;
import com.teamacronymcoders.contenttweaker.api.ctobjects.resourcelocation.CTResourceLocation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.MCWorld;
import com.teamacronymcoders.contenttweaker.api.utils.CTUtils;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockColorSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity.TileEntityContent;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.world.MCBlockAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class BlockContent extends BlockBase implements IHasBlockColor, IHasItemColor {
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
        if (this.blockRepresentation.hasGravity()) {
            world.scheduleUpdate(pos, this, tickRate(world));
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        activateBlockAction(this.blockRepresentation.getOnBlockBreak(), world, pos, state);
        super.breakBlock(world, pos, state);

    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity){
        if (this.blockRepresentation.isWitherProof() && (entity instanceof EntityWither || entity instanceof EntityWitherSkull)) {
            return false;
        }
        return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity entity, Explosion explosion) {
        if (this.blockRepresentation.isWitherProof() && (entity instanceof EntityWither || entity instanceof EntityWitherSkull)) {
            return 2000.0F;
        }
        return super.getExplosionResistance(world, pos, entity, explosion);
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
        if (this.blockRepresentation.hasGravity() && !world.isRemote && (world.isAirBlock(pos.down()) || BlockFalling.canFallThrough(world.getBlockState(pos.down()))) &&
                pos.getY() >= 0 && world.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
            world.spawnEntity(new EntityFallingBlock(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos)));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (this.blockRepresentation.hasGravity()) {
            world.scheduleUpdate(pos, this, tickRate(world));
        } else {
            super.neighborChanged(state, world, pos, blockIn, fromPos);
        }
    }

    @Override
    public int tickRate(World world) {
        return this.blockRepresentation.hasGravity() ? 2 : super.tickRate(world);
    }

    public void activateBlockAction(IBlockAction blockAction, World world, BlockPos blockPos, IBlockState blockState) {
        if (blockAction != null) {
            blockAction.onBlockAction(new MCWorld(world), new MCBlockPos(blockPos), new MCBlockState(blockState));
        }
    }

    @Override
    @Nonnull
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) {
        return blockRepresentation.getMobilityFlag().getInternal();
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return this.blockRepresentation.isPassable();
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, @Nonnull BlockPos pos) {
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

    @Override
    public List<IGeneratedModel> getGeneratedModels() {
        List<IGeneratedModel> models = Lists.newArrayList();
        this.getResourceLocations(Lists.newArrayList()).forEach(resourceLocation ->  {
            TemplateFile templateFile = TemplateManager.getTemplateFile("colored_block");
            Map<String, String> replacements = Maps.newHashMap();

            replacements.put("texture", Optional.ofNullable(blockRepresentation.getTextureLocation())
                    .map(CTResourceLocation::getInternal)
                    .map(ResourceLocation::toString)
                    .orElseGet(() -> new ResourceLocation(resourceLocation.getNamespace(),
                            "blocks/" + resourceLocation.getPath()).toString()));

            templateFile.replaceContents(replacements);

            models.add(new GeneratedModel(resourceLocation.getPath(), ModelType.BLOCKSTATE,
                    templateFile.getFileContents()));
        });

        return models;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        IBlockColorSupplier blockColorSupplier = blockRepresentation.getBlockColorSupplier();
        if (blockColorSupplier == null) {
            return -1;
        }
        crafttweaker.api.world.IBlockAccess blockAccess = null;
        if (world instanceof World) {
            blockAccess = new MCWorld((World) world);
        } else if (world != null) {
            blockAccess = new MCBlockAccess(world);
        }
        IBlockPos blockPos = pos == null ? null : new MCBlockPos(pos);
        CTColor color = blockColorSupplier.getColor(new MCBlockState(state), blockAccess, blockPos, tintIndex);
        return color == null ? -1 : color.getIntColor();
    }

    @Override
    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
        return Optional.ofNullable(blockRepresentation.getItemColorSupplier())
                .map(supplier -> supplier.getColor(new MCItemStack(stack), tintIndex))
                .map(CTColor::getIntColor)
                .orElse(-1);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player) {
a        return blockRepresentation.canSilkHarvest;
    }
}

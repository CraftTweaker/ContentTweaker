package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.client.models.generator.IHasGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.GeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.ModelType;
import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;
import com.teamacronymcoders.contenttweaker.api.MissingFieldsException;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.MCBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.MCBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player.CTPlayer;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Hand;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.MCMutableItemStack;
import com.teamacronymcoders.contenttweaker.api.ctobjects.resourcelocation.CTResourceLocation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.MCWorld;
import com.teamacronymcoders.contenttweaker.api.utils.CTUtils;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.Position3f;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.util.MCPosition3f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class ItemContent extends ItemBase implements IHasModel, IHasGeneratedModel, IHasItemColor {
    private ItemRepresentation itemRepresentation;
    private CreativeTabs creativeTab;
    private IBaseMod mod;
    private EnumAction itemUseAction;
    private EnumRarity rarity;

    public ItemContent(ItemRepresentation itemRepresentation) {
        super(itemRepresentation.getUnlocalizedName());
        this.itemRepresentation = itemRepresentation;
        checkFields();
        setFields();
    }

    @Override
    public IBaseMod getMod() {
        return mod;
    }

    @Override
    public void setMod(IBaseMod mod) {
        this.mod = mod;
    }

    /* Beginning of Representation stuff */
    public void checkFields() {
        List<String> missingFields = new ArrayList<>();
        if (this.itemRepresentation.getUnlocalizedName() == null) {
            missingFields.add("unlocalizedName");
        }
        if (!missingFields.isEmpty()) {
            throw new MissingFieldsException("ItemRepresentation", missingFields);
        }
    }

    public void setFields() {
        this.setUnlocalizedName(this.itemRepresentation.getUnlocalizedName());
        if (this.itemRepresentation.getCreativeTab() != null) {
            Object creativeTab = this.itemRepresentation.getCreativeTab().getInternal();
            if (creativeTab instanceof CreativeTabs) {
                this.setCreativeTab((CreativeTabs) this.itemRepresentation.getCreativeTab().getInternal());
            }
        }
        this.setMaxStackSize(this.itemRepresentation.getMaxStackSize());
        this.setHarvestLevel(this.itemRepresentation.getToolClass(), this.itemRepresentation.getToolLevel());
        this.itemUseAction = CTUtils.getEnum(this.itemRepresentation.getItemUseAction(), EnumAction.class);
        this.rarity = CTUtils.getEnum(this.itemRepresentation.getRarity(), EnumRarity.class);
        if (this.itemRepresentation.getMaxDamage() > 0) {
            this.setMaxDamage(this.itemRepresentation.getMaxDamage());
        }
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(@Nonnull ItemStack itemStack) {
        return this.rarity;
    }

    @Override
    public float getSmeltingExperience(@Nonnull ItemStack itemStack) {
        return this.itemRepresentation.getSmeltingExperience();
    }

    @Override
    public boolean isBeaconPayment(@Nonnull ItemStack itemStack) {
        return this.itemRepresentation.isBeaconPayment();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    @Override
    @Nonnull
    public Item setCreativeTab(@Nonnull CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return this.itemRepresentation.isGlowing() || super.hasEffect(stack);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        EnumActionResult enumActionResult = EnumActionResult.PASS;
        ItemStack itemStack = player.getHeldItem(hand);
        if (itemRepresentation.getItemRightClick() != null) {
            String stringResult = itemRepresentation.getItemRightClick().onRightClick(new MCMutableItemStack(itemStack),
                    new MCWorld(world), new CTPlayer(player), hand.name());
            if (stringResult != null) {
                enumActionResult = EnumActionResult.valueOf(stringResult.toUpperCase(Locale.US));
            }
        }
        return new ActionResult<>(enumActionResult, itemStack);
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        EnumActionResult actionResult = EnumActionResult.PASS;
        if (Objects.nonNull(itemRepresentation.getOnItemUse())) {
            Position3f blockTouch = new MCPosition3f(hitX, hitY, hitZ);
            actionResult = itemRepresentation.getOnItemUse().useItem(new CTPlayer(player), new MCWorld(world), new MCBlockPos(pos),
                    Hand.of(hand), Facing.of(facing), blockTouch).getInternal();
        }
        return actionResult;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return Optional.ofNullable(itemRepresentation.getItemDestroySpeed())
                .map(value -> value.getDestroySpeed(new MCMutableItemStack(stack), new MCBlockState(state)))
                .orElseGet(() -> super.getDestroySpeed(stack, state));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return Optional.ofNullable(itemRepresentation.getItemDestroyedBlock())
                .map(value -> value.onBlockDestroyed(new MCMutableItemStack(stack), new MCWorld(world),
                        new MCBlockState(state), new MCBlockPos(pos), new MCEntityLivingBase(entityLiving)))
                .orElseGet(() -> super.onBlockDestroyed(stack, world, state, pos, entityLiving));
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return this.itemUseAction;
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add(this.getUnlocalizedName().substring(5));
        return modelNames;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this));
        return itemStacks;
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public List<IGeneratedModel> getGeneratedModels() {
        List<IGeneratedModel> models = Lists.newArrayList();
        TemplateFile templateFile = TemplateManager.getTemplateFile("item_model");
        Map<String, String> replacements = Maps.newHashMap();

        replacements.put("texture", Optional.ofNullable(itemRepresentation.getTextureLocation())
                .map(CTResourceLocation::getInternal)
                .map(ResourceLocation::toString)
                .orElseGet(() -> "contenttweaker:items/" + itemRepresentation.getUnlocalizedName()));
        templateFile.replaceContents(replacements);
        models.add(new GeneratedModel(itemRepresentation.getUnlocalizedName(), ModelType.ITEM_MODEL, templateFile.getFileContents()));
        return models;
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack) {
        return itemRepresentation.getItemGetContainerItem() != null || super.hasContainerItem(itemStack);
    }

    @Override
    @Nonnull
    public ItemStack getContainerItem(@Nonnull final ItemStack itemStack) {
        return Optional.ofNullable(itemRepresentation.getItemGetContainerItem())
                .map(getContainerItem -> getContainerItem.getContainerItem(new MCItemStack(itemStack)))
                .map(result -> (ItemStack) result.getInternal())
                .orElseGet(() -> super.getContainerItem(itemStack));
    }

    @Override
    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
        return itemRepresentation.getItemColorSupplier().getColor(new MCItemStack(stack), tintIndex).getIntColor();
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return Optional.ofNullable(itemRepresentation.getLocalizedNameSupplier())
                .map(supplier -> supplier.getLocalizedName(new MCItemStack(stack)))
                .orElseGet(() -> super.getItemStackDisplayName(stack));
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if(itemRepresentation.onItemUpdate == null)
            super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        else
            itemRepresentation.onItemUpdate.onItemUpdate(new MCMutableItemStack(stack), new MCWorld(worldIn), entityIn instanceof EntityPlayer ? new CTPlayer((EntityPlayer) entityIn) : CraftTweakerMC.getIEntity(entityIn), itemSlot, isSelected);
    }
}

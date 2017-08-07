package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.contenttweaker.api.MissingFieldsException;
import com.teamacronymcoders.contenttweaker.api.wrappers.world.MCWorld;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemContent extends Item implements IHasModel {
    private ItemRepresentation itemRepresentation;
    private CreativeTabs creativeTab;

    public ItemContent(ItemRepresentation itemRepresentation) {
        this.itemRepresentation = itemRepresentation;
        checkFields();
        setFields();
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
        this.setCreativeTab(this.itemRepresentation.getInternalCreativeTab());
        this.setMaxStackSize(this.itemRepresentation.getMaxStackSize());
        this.setHarvestLevel(this.itemRepresentation.getToolClass(), this.itemRepresentation.getToolLevel());
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(@Nonnull ItemStack itemStack) {
        return this.itemRepresentation.getInternalRarity();
    }

    @Override
    public float getSmeltingExperience(@Nonnull ItemStack itemStack) {
        return this.itemRepresentation.getSmeltingExperience();
    }

    @Override
    public boolean isBeaconPayment(@Nonnull ItemStack itemStack) {
        return this.itemRepresentation.isBeaconPayment();
    }

    @Nonnull
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

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        EnumActionResult enumActionResult = EnumActionResult.PASS;
        ItemStack itemStack = player.getHeldItem(hand);
        if (itemRepresentation.getItemRightClick() != null) {
            String stringResult = itemRepresentation.getItemRightClick().onRightClick(new MCItemStack(itemStack),
                    new MCWorld(world), new MCPlayer(player), hand.name());
            if (stringResult != null) {
                enumActionResult = EnumActionResult.valueOf(stringResult.toUpperCase(Locale.US));
            }
        }
        return new ActionResult<>(enumActionResult, itemStack);
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return this.itemRepresentation.getInternalItemUseAction();
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
}

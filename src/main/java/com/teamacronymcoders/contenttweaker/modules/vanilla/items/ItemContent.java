package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.MissingFieldsException;
import com.teamacronymcoders.contenttweaker.api.wrappers.world.MCWorld;
import minetweaker.mc1102.item.MCItemStack;
import minetweaker.mc1102.player.MCPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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
import java.util.ArrayList;
import java.util.List;

public class ItemContent extends Item {
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
        return this.itemRepresentation.getInteralRarity();
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
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack itemStack, @Nonnull World world,
                                                    @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        EnumActionResult enumActionResult = EnumActionResult.PASS;
        if (itemRepresentation.getItemRightClick() != null) {
            String stringResult = itemRepresentation.getItemRightClick().onRightClick(new MCItemStack(itemStack),
                    new MCWorld(world), new MCPlayer(player), hand);
            if (stringResult != null) {
                enumActionResult = EnumActionResult.valueOf(stringResult);
            }
        }
        return new ActionResult<>(enumActionResult, itemStack);
    }
}

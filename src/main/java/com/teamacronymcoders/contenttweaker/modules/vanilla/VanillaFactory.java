package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.fluids.FluidRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.food.ItemFoodRepresentation;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.VanillaFactory")
public class VanillaFactory {
    @ZenMethod
    public static BlockRepresentation createBlock(String unlocalizedName, IBlockMaterialDefinition material) {
        BlockRepresentation blockRepresentation = new BlockRepresentation();
        blockRepresentation.setUnlocalizedName(unlocalizedName);
        blockRepresentation.setBlockMaterial(material);
        return blockRepresentation;
    }

    @ZenMethod
    public static ItemRepresentation createItem(String unlocalizedName) {
        ItemRepresentation itemRepresentation = new ItemRepresentation();
        itemRepresentation.setUnlocalizedName(unlocalizedName);
        return itemRepresentation;
    }

    @ZenMethod
    public static ItemFoodRepresentation createItemFood(String unlocalizedName, int healAmount) {
        ItemFoodRepresentation itemRepresentation = new ItemFoodRepresentation();
        itemRepresentation.setUnlocalizedName(unlocalizedName);
        itemRepresentation.setHealAmount(healAmount);
        return itemRepresentation;
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, IItemStack iItemStack) {
        CreativeTabRepresentation creativeTab = new CreativeTabRepresentation();
        creativeTab.setUnlocalizedName(unlocalizedName);
        if (iItemStack.getInternal() instanceof ItemStack) {
            creativeTab.setIconStack((ItemStack) iItemStack.getInternal());
        }
        return creativeTab;
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, ItemRepresentation iItem) {
        return createCreativeTab(unlocalizedName, new MCItemStack(new ItemStack(iItem.getInternal())));
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, BlockRepresentation iBlock) {
        return createCreativeTab(unlocalizedName, new MCItemStack(new ItemStack(iBlock.getInternal())));
    }

    @ZenMethod
    public static FluidRepresentation createFluid(String unlocalizedName, int color) {
        return new FluidRepresentation(unlocalizedName, color);
    }
}

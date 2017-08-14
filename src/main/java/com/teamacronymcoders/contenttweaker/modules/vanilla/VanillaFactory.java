package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.IBlock;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.IItem;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.VanillaFactory")
public class VanillaFactory {
    @ZenMethod
    public static IBlock createBlock(String unlocalizedName, IMaterialDefinition material) {
        BlockRepresentation blockRepresentation = new BlockRepresentation();
        blockRepresentation.setUnlocalizedName(unlocalizedName);
        blockRepresentation.setBlockMaterial(material);
        return blockRepresentation;
    }

    @ZenMethod
    public static IItem createItem(String unlocalizedName) {
        ItemRepresentation itemRepresentation = new ItemRepresentation();
        itemRepresentation.setUnlocalizedName(unlocalizedName);
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
    public static ICreativeTab createCreativeTab(String unlocalizedName, IItem iItem) {
        return createCreativeTab(unlocalizedName, new MCItemStack(new ItemStack((Item) iItem.getInternal())));
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, IBlock iBlock) {
        return createCreativeTab(unlocalizedName, new MCItemStack(new ItemStack((Block) iBlock.getInternal())));
    }
}

package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.IBlock;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabContent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabRepresentation;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.VanillaFactory")
public class VanillaFactory {
    @ZenMethod
    public static IBlock createBlock(String unlocalizedName) {
        BlockRepresentation blockRepresentation = new BlockRepresentation();
        blockRepresentation.setUnlocalizedName(unlocalizedName);
        return blockRepresentation;
    }

    @ZenMethod
    public static void registerBlock(IBlock block) {
        if(block.getInternal() instanceof IRepresentation) {
            ((IRepresentation) block.getInternal()).register();
        }
    }

    @ZenMethod
    public static void createAndRegisterCreativeTab(String unlocalizedName, IItemStack iItemStack) {
        CreativeTabRepresentation creativeTab = new CreativeTabRepresentation();
        creativeTab.setUnlocalizedName(unlocalizedName);
        if(iItemStack.getInternal() instanceof ItemStack) {
            creativeTab.setIconStack((ItemStack) iItemStack.getInternal());
        }
        new CreativeTabContent(creativeTab);
    }
}

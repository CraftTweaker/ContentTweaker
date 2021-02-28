package com.blamejared.contenttweaker.color;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.functions.IBlockColorSupplier;
import com.blamejared.contenttweaker.api.functions.IItemColorSupplier;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) ->
                        VanillaFactory.REGISTRY.getFunction(((IIsCotItem) stack.getItem()), IItemColorSupplier.class)
                                .map(iItemColorSupplier -> iItemColorSupplier.apply(new MCItemStack(stack), tintIndex))
                                .orElse(-1),
                VanillaFactory.REGISTRY.getItems().stream().filter(IIsCotItem::allowTinted).map(IForgeItem::getItem).toArray(IItemProvider[]::new));
    }

    @SubscribeEvent
    public static void onBlockColor(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, world, pos, tintIndex) ->
                        VanillaFactory.REGISTRY.getFunction(((IIsCoTBlock) state.getBlock()), IBlockColorSupplier.class)
                                .map(iBlockColorSupplier -> iBlockColorSupplier.apply(state, world, pos, tintIndex))
                                .orElse(-1),
                VanillaFactory.REGISTRY.getBlocks().stream().filter(IIsCoTBlock::allowTinted).map(IForgeBlock::getBlock).toArray(Block[]::new)
        );
    }
}

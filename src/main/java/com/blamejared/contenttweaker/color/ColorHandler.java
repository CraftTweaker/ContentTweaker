package com.blamejared.contenttweaker.color;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.blocks.types.advanced.CoTBlockAdvanced;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        VanillaFactory.REGISTRY.getItems().stream()
                .filter(IItemHasColor.class::isInstance)
                .map(IItemHasColor.class::cast)
                .forEach(item -> event.getItemColors().register(item::getColor, item));
    }

    @SubscribeEvent
    public static void onBlockColor(ColorHandlerEvent.Block event) {
        VanillaFactory.REGISTRY.getItems().stream()
                .filter(CoTBlockAdvanced.class::isInstance)
                .map(CoTBlockAdvanced.class::cast)
                .forEach(block -> event.getBlockColors().register(block::getColor, block));
    }
}

package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "contenttweaker")
public class IRecipe {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerIRecipe(RegistryEvent.Register<net.minecraft.item.crafting.IRecipe> event){
            BlockRegistry blockRegistry = ContentTweaker.instance.getRegistryHolder().getRegistry(BlockRegistry.class, "BLOCK");
            blockRegistry.getEntries().forEach((name, block) ->
                    ForgeRegistries.BLOCKS.register(block.setRegistryName(name)));
            blockRegistry.registryEvent();

            ItemRegistry itemRegistry = ContentTweaker.instance.getRegistryHolder().getRegistry(ItemRegistry.class, "ITEM");
            itemRegistry.getEntries().forEach((name, item) ->
                    ForgeRegistries.ITEMS.register(item.setRegistryName(name)));
            itemRegistry.registryEvent();
    }
}
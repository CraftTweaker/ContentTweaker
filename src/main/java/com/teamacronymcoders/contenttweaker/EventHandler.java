package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import crafttweaker.mc1120.events.ScriptRunEvent;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.MultiLayerModel;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = "contenttweaker")
public class EventHandler {
    @SubscribeEvent
    public static void postScriptRun(ScriptRunEvent.Post event) {
        ModContainer currentModContainer = Loader.instance().activeModContainer();
        ModContainer contentTweakerContainer = null;
        Iterator<ModContainer> modContainers = Loader.instance().getActiveModList().iterator();
        while (contentTweakerContainer == null && modContainers.hasNext()) {
            ModContainer modContainer = modContainers.next();
            if ("contenttweaker".equalsIgnoreCase(modContainer.getModId())) {
                contentTweakerContainer = modContainer;
            }
        }
        if (contentTweakerContainer != null) {
            Loader.instance().setActiveModContainer(contentTweakerContainer);
        }

        MaterialUser materialUser = ContentTweaker.instance.getMaterialUser();
        if (materialUser != null) {
            materialUser.finishUp();
        }

        SubBlockSystem subBlockSystem = ContentTweaker.instance.getSubBlockSystem();
        if (subBlockSystem != null) {
            subBlockSystem.createBlocks();
        }

        BlockRegistry blockRegistry = ContentTweaker.instance.getRegistryHolder().getRegistry(BlockRegistry.class, "BLOCK");
        blockRegistry.getEntries().forEach((name, block) ->
                ForgeRegistries.BLOCKS.register(block.setRegistryName(name)));
        blockRegistry.registryEvent();

        ItemRegistry itemRegistry = ContentTweaker.instance.getRegistryHolder().getRegistry(ItemRegistry.class, "ITEM");
        itemRegistry.getEntries().forEach((name, item) ->
                ForgeRegistries.ITEMS.register(item.setRegistryName(name)));
        itemRegistry.registryEvent();

        Loader.instance().setActiveModContainer(currentModContainer);
    }
}
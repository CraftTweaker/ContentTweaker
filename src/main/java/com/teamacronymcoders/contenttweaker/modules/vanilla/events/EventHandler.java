package com.teamacronymcoders.contenttweaker.modules.vanilla.events;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ctobjects.inventory.ICTInventory;
import com.teamacronymcoders.contenttweaker.api.ctobjects.inventory.MCInventory;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

@EventBusSubscriber(modid = ContentTweaker.MOD_ID)
public class EventHandler {
    public static void onCraft(ItemCraftedEvent event) {
        IItemStack itemStack = new MCItemStack(event.crafting);
        ICTInventory inventory = new MCInventory(event.craftMatrix);

        Events.getOnItemCraftFunctions().forEach(onItemCraft -> onItemCraft.onCrafted(itemStack, inventory));
    }
}

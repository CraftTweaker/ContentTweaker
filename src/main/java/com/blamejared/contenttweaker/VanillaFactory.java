package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.actions.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.file_handling.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.thread.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class VanillaFactory {
    
    /**
     * We will only generate data pack entries and resource pack entries for these mod ids.
     * ModIds can be added by calling {@link #generateStuffForMyModId(String)}
     */
    private static final Set<String> modIdsToGenerateStuffFor = new HashSet<>();
    private static final CoTRegistry registry = new CoTRegistry();
    
    private static boolean registerAllowed = true;
    
    /**
     * Checks if adding content is allowed at the moment.
     *
     * Only while this is true calls to {@link #queueItemForRegistration(IIsCotItem)} or {@link #queueBlockForRegistration(IIsCoTBlock)} will succeed.
     */
    public static boolean isRegisterAllowed() {
        return registerAllowed;
    }
    
    /**
     * Whitelists this modid to have resource packs and data packs generated for.
     * Should generally only be used for CoT addons.
     *
     * @param myModId The modid to whitelist
     */
    public static void generateStuffForMyModId(String myModId) {
        modIdsToGenerateStuffFor.add(myModId);
    }
    
    /**
     * Queues this block to be registered later.
     * Needs to be done before {@link #forbidRegistration()} has been called!
     * Blocks will actually be registered at {@link #complete()}.
     *
     * @param block The block to enqueue.
     */
    public static void queueBlockForRegistration(IIsCoTBlock block) {
        CraftTweakerAPI.apply(new ActionQueueBlockForRegistration(block, registry));
    }
    
    /**
     * Queues this item to be registered later.
     * Needs to be done before {@link #forbidRegistration()} has been called!
     * Items will actually be registered at {@link #complete()}.
     *
     * @param item The item to enqueue.
     */
    public static void queueItemForRegistration(IIsCotItem item) {
        CraftTweakerAPI.apply(new ActionQueueItemForRegistration(item, registry));
    }
    
    /**
     * Prevents any more calls to
     * {@link #queueBlockForRegistration(IIsCoTBlock)} or {@link #queueItemForRegistration(IIsCotItem)}
     * from succeeding.
     *
     * Will make {@link #isRegisterAllowed()} false
     */
    static void forbidRegistration() {
        registerAllowed = false;
    }
    
    /**
     * Registers the blocks and creates the resource pack and data pack.
     */
    static void complete() {
        registry.getBlocksAsVanillaBlocks().forEach(value -> {
            CraftTweakerAPI.logDebug("Registering Block '%s'", value.getRegistryName());
            ForgeRegistries.BLOCKS.register(value);
        });
        
        registry.getItemsAsVanillaItems().forEach(value -> {
            CraftTweakerAPI.logDebug("Registering Item '%s'", value.getRegistryName());
            ForgeRegistries.ITEMS.register(value);
        });
    
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> VanillaFactory::writeResourcePack);
        writeDataPack();
    }
    
    private static void writeResourcePack() {
        if(!EffectiveSide.get().isClient()) {
            CraftTweakerAPI.logInfo("Skipping writing resources for ContentTweaker, because we are on a server");
            return;
        }
        
        final ResourcePackInfo resourcePackInfo = ResourcePackInfo.get();
        if(resourcePackInfo == null) {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no resource pack will be generated!");
            return;
        }
        
        resourcePackInfo.createResourcePackIfNotExists();
        registry.getAssetResources()
                .filter(w -> modIdsToGenerateStuffFor.contains(w.getModId()))
                .forEach(w -> {
                    w.writeContentUsing(resourcePackInfo.getResourcePackDirectory());
                    w.onWrite();
                });
    }
    
    private static void writeDataPack() {
        final ResourcePackInfo resourcePackInfo = ResourcePackInfo.get();
        if(resourcePackInfo == null) {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no data pack will be generated!");
            return;
        }
        
        resourcePackInfo.createDataPackIfNotExists();
        registry.getDataResources()
                .filter(w -> modIdsToGenerateStuffFor.contains(w.getModId()))
                .forEach(w -> {
                    w.writeContentUsing(resourcePackInfo.getDataPackDirectory());
                    w.onWrite();
                });
    }
}

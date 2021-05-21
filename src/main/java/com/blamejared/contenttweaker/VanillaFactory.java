package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.actions.ActionQueueBlockForRegistration;
import com.blamejared.contenttweaker.actions.ActionQueueFluidForRegistration;
import com.blamejared.contenttweaker.actions.ActionQueueItemForRegistration;
import com.blamejared.contenttweaker.api.CoTRegistry;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.file_handling.ResourcePackInfo;
import com.blamejared.contenttweaker.blocks.render.BlockRenderType;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class VanillaFactory {

    public static final CoTRegistry REGISTRY = new CoTRegistry();
    /**
     * We will only generate data pack entries and resource pack entries for these mod ids.
     * ModIds can be added by calling {@link #generateStuffForMyModId(String)}
     */
    private static final Set<String> modIdsToGenerateStuffFor = new HashSet<>();
    private static boolean registerAllowed = true;

    /**
     * Checks if adding content is allowed at the moment.
     * <p>
     * Only while this is true calls to {@link #queueItemForRegistration(IIsCotItem)} or {@link #queueBlockForRegistration(IIsCoTBlock, BlockRenderType)} will succeed.
     */
    public static boolean isRegisterAllowed() {
        return registerAllowed;
    }

    /**
     * Whitelists this mod-id to have resource packs and data packs generated for.
     * Should generally only be used for CoT addons.
     *
     * @param myModId The mod-id to whitelist
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
    public static void queueBlockForRegistration(IIsCoTBlock block, BlockRenderType renderType) {
        CraftTweakerAPI.apply(new ActionQueueBlockForRegistration(block, REGISTRY, renderType));
    }

    /**
     * Queues this item to be registered later.
     * Needs to be done before {@link #forbidRegistration()} has been called!
     * Items will actually be registered at {@link #complete()}.
     *
     * @param item The item to enqueue.
     */
    public static void queueItemForRegistration(IIsCotItem item) {
        CraftTweakerAPI.apply(new ActionQueueItemForRegistration(item, REGISTRY));
    }

    public static void queueFluidForRegistration(IIsCotFluid fluid) {
        CraftTweakerAPI.apply(new ActionQueueFluidForRegistration(fluid, REGISTRY));
    }

    /**
     * Prevents any more calls to
     * {@link #queueBlockForRegistration(IIsCoTBlock, BlockRenderType)} or {@link #queueItemForRegistration(IIsCotItem)}
     * from succeeding.
     * <p>
     * Will make {@link #isRegisterAllowed()} false
     */
    static void forbidRegistration() {
        registerAllowed = false;
    }

    /**
     * Registers the blocks and creates the resource pack and data pack.
     */
    static void complete() {
        REGISTRY.getBlocksAsVanillaBlocks().forEach(value -> {
            CraftTweakerAPI.logDebug("Registering Block '%s'", value.getRegistryName());
            ForgeRegistries.BLOCKS.register(value);
        });

        REGISTRY.getItemsAsVanillaItems().forEach(value -> {
            CraftTweakerAPI.logDebug("Registering Item '%s'", value.getRegistryName());
            ForgeRegistries.ITEMS.register(value);
        });

        REGISTRY.getFluids().forEach(cotFluid -> {
                    CraftTweakerAPI.logDebug("Registering Fluid '%s'", cotFluid.getRegistryName());
                    if (cotFluid.getFluidBlock() != null) {
                        ForgeRegistries.BLOCKS.register(cotFluid.getFluidBlock());
                    }
                    ForgeRegistries.FLUIDS.register(cotFluid.get());
                });
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> VanillaFactory::writeResourcePack);
        writeDataPack();
    }

    private static void writeResourcePack() {
        if (!EffectiveSide.get().isClient()) {
            CraftTweakerAPI.logInfo("Skipping writing resources for ContentTweaker, because we are on a server");
            return;
        }

        final ResourcePackInfo resourcePackInfo = ResourcePackInfo.get();
        if (resourcePackInfo == null) {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no resource pack will be generated!");
            return;
        }

        resourcePackInfo.createResourcePackIfNotExists();
        REGISTRY.getAssetResources().filter(w -> modIdsToGenerateStuffFor.contains(w.getModId())).forEach(w -> {
            w.writeContentUsing(resourcePackInfo.getResourcePackDirectory());
            w.onWrite();
        });
    }

    private static void writeDataPack() {
        final ResourcePackInfo resourcePackInfo = ResourcePackInfo.get();
        if (resourcePackInfo == null) {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no data pack will be generated!");
            return;
        }

        resourcePackInfo.createDataPackIfNotExists();
        REGISTRY.getDataResources().filter(w -> modIdsToGenerateStuffFor.contains(w.getModId())).forEach(w -> {
            w.writeContentUsing(resourcePackInfo.getDataPackDirectory());
            w.onWrite();
        });
    }

}

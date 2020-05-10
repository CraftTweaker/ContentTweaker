package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.*;

import java.util.function.*;

@Mod("contenttweaker")
public class ContentTweaker {
    
    public static final String MOD_ID = "contenttweaker";
    public static final String NAME = "ContentTweaker";
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(EventPriority.LOW, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerTEProvider);
        
        //if(EffectiveSide.get().isClient()) {
        //    ResourcePackInformation.createResourcePackFolders();
        //}
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
    }
    
    /**
     * Subscribed at low prio so that the blocks and items <i>should</i> already be there
     */
    private void registerItems(final RegistryEvent.Register<Block> registryEvent) {
        if(registryEvent.getRegistry() != ForgeRegistries.BLOCKS) {
            //Why though?
            return;
        }
        
        // VanillaFactory.registerAllowed = true;
        CraftTweakerAPI.logWarning("Hello from CoT!");
        CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().execute().setLoaderName(MOD_ID));
        // VanillaFactory.registerAllowed = false;
        
        // if(EffectiveSide.get().isClient()) {
        //     VanillaFactory.writeResourcePack();
        // }
        VanillaFactory.complete();
    }
    
    private void registerTEProvider(final RegistryEvent.Register<TileEntityType<?>> registryEvent) {
        if(registryEvent.getRegistry() != ForgeRegistries.TILE_ENTITIES) {
            return;
        }
    
        for(CoTBlockTile allBlock : MachineBlockRegistry.ALL_BLOCKS) {
            final ResourceLocation location = allBlock.getMCResourceLocation().getInternal();
            final Supplier<CoTTile> factory = () -> new CoTTile(location, new CoTCapabilityInstanceManager(allBlock));
            //noinspection ConstantConditions
            final TileEntityType<CoTTile> type = TileEntityType.Builder.create(factory, allBlock)
                    .build(null);
            type.setRegistryName(location);
            registryEvent.getRegistry().register(type);
            MachineBlockRegistry.TYPES.put(location, type);
        }
    }
}

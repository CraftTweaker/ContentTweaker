package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.container.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.extensions.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.function.*;

@Mod("contenttweaker")
public class ContentTweaker {
    
    public static final String MOD_ID = "contenttweaker";
    public static final String NAME = "ContentTweaker";
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(EventPriority.LOW, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerTEProvider);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerContainerTypes);
        
        //if(EffectiveSide.get().isClient()) {
        //    ResourcePackInformation.createResourcePackFolders();
        //}
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
        for(CoTBlockTile allBlock : MachineBlockRegistry.ALL_BLOCKS) {
            final ResourceLocation location = allBlock.getMCResourceLocation().getInternal();
            final ContainerType<CoTContainer> containerType = MachineBlockRegistry.CONTAINER_TYPES.get(location);
            registerScreen(allBlock, containerType);
        }
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
            final Supplier<CoTTile> factory;
            if(allBlock.hasTickingCapabilities()) {
                factory = () -> new CoTTileTicking(location, new CoTCapabilityInstanceManager(allBlock));
            } else {
                factory = () -> new CoTTile(location, new CoTCapabilityInstanceManager(allBlock));
            }
            //noinspection ConstantConditions
            final TileEntityType<CoTTile> type = TileEntityType.Builder.create(factory, allBlock)
                    .build(null);
            type.setRegistryName(location);
            
            
            registryEvent.getRegistry().register(type);
            MachineBlockRegistry.TILE_TYPES.put(location, type);
        }
    }
    
    private void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> registryEvent) {
        if(registryEvent.getRegistry() != ForgeRegistries.CONTAINERS) {
            return;
        }
        
        for(CoTBlockTile allBlock : MachineBlockRegistry.ALL_BLOCKS) {
            final ContainerType<CoTContainer> coTContainerContainerType = IForgeContainerType.create((windowId, inv, data) -> {
                Objects.requireNonNull(inv, "Inventory may not be null");
                Objects.requireNonNull(data, "Data may not be null");
                final BlockPos blockPos = data.readBlockPos();
                final TileEntity tileEntity = inv.player.world.getTileEntity(blockPos);
                if(!(tileEntity instanceof CoTTile)) {
                    throw new IllegalArgumentException("Tile is not a CoTTile!" + tileEntity);
                }
                return ((CoTTile) tileEntity).createMenu(windowId, inv, inv.player);
            });
            
            final ResourceLocation location = allBlock.getMCResourceLocation().getInternal();
            coTContainerContainerType.setRegistryName(location);
            MachineBlockRegistry.CONTAINER_TYPES.put(location, coTContainerContainerType);
            registryEvent.getRegistry().register(coTContainerContainerType);
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    private void registerScreen(CoTBlockTile block, ContainerType<CoTContainer> containerType) {
        //Reason: Gradle hiccups and cannot infer it correctly, so better leave them in?
        //noinspection RedundantTypeArguments
        ScreenManager.<CoTContainer, CoTScreen> registerFactory(containerType, (container, playerInventory, textComponent) -> {
            final CoTCapabilityConfigurationManager capabilityConfiguration = block.getCapabilityConfiguration();
            return capabilityConfiguration.createScreen(container, playerInventory, textComponent);
        });
    }
}

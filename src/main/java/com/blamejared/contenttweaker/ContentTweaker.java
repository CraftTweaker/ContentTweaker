package com.blamejared.contenttweaker;

import com.blamejared.crafttweaker.api.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.*;

@Mod("contenttweaker")
public class ContentTweaker {
    
    public static final String MOD_ID = "contenttweaker";
    public static final String NAME = "ContentTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerItems);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
    }
    
    private void registerItems(final RegistryEvent.Register<Item> registryEvent){
        if(registryEvent.getRegistry() != ForgeRegistries.ITEMS) {
            //Why though?
            return;
        }
        
        CraftTweakerAPI.logWarning("Hello from CoT!");
        VanillaFactory.registry = registryEvent.getRegistry();
        CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().execute().setLoaderName(MOD_ID));
        VanillaFactory.registerLocked = true;
    }
}

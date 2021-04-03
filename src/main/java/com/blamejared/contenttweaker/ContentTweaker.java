package com.blamejared.contenttweaker;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.impl.commands.script_examples.ExampleCollectionEvent;
import net.minecraft.block.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

@Mod(ContentTweaker.MOD_ID)
public class ContentTweaker {
    
    public static final String MOD_ID = "contenttweaker";
    public static final String NAME = "ContentTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        VanillaFactory.generateStuffForMyModId(MOD_ID);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, EventPriority.LOW, this::registerItems);
    }

    @SubscribeEvent
    public void addExampleScriptFiles(ExampleCollectionEvent event) {
        event.addResource(new ResourceLocation(MOD_ID, "contenttweaker_simple_walkthrough"));
    }
    
    /**
     * Loads the scripts and registers the items afterwards.
     * <p>
     * Subscribed at low priority so that the blocks <i>should</i> already be there.
     * Hopefully also some items, but I wouldn't count on it
     */
    private void registerItems(final RegistryEvent.Register<Block> registryEvent) {
        final ScriptLoadingOptions scriptLoadingOptions = new ScriptLoadingOptions().setLoaderName(MOD_ID).execute();
        
        CraftTweakerAPI.loadScripts(scriptLoadingOptions);
        VanillaFactory.forbidRegistration();
        VanillaFactory.complete();
    }
}

package com.blamejared.contenttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.impl.commands.script_examples.ExampleCollectionEvent;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ContentTweaker.MOD_ID)
public class ContentTweaker {
    
    public static final String MOD_ID = "contenttweaker";
    public static final String NAME = "ContentTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        VanillaFactory.generateStuffForMyModId(MOD_ID);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, EventPriority.LOW, this::registerItems);
        MinecraftForge.EVENT_BUS.addListener(this::addExampleScriptFiles);
    }

    private void addExampleScriptFiles(ExampleCollectionEvent event) {
        event.addResource(new ResourceLocation(MOD_ID, "contenttweaker_simple_walkthrough"));
        event.addResource(new ResourceLocation(MOD_ID, "contenttweaker_advanced_function"));
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

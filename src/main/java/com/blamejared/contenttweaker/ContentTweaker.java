package com.blamejared.contenttweaker;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

@Mod("contenttweaker")
public class ContentTweaker {
    
    public static final String MODID = "@MODID@";
    public static final String NAME = "@NAME@";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public ContentTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        VanillaFactory.registerLocked = true;
        LOG.info("{} has loaded successfully!", NAME);
    }
}

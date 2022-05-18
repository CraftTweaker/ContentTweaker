package com.blamejared.contenttweaker.forge;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.forge.resource.ForgeResourceManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ContentTweakerConstants.MOD_ID)
public final class ContentTweaker {

    public ContentTweaker() {
        ContentTweakerCore.core().initialize();
        ForgeResourceManager.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

}

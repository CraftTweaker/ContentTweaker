package com.teamacronymcoders.contenttweaker.proxies;

import com.teamacronymcoders.contenttweaker.renderers.ErrorSilencingLoader;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public void createErrorSilencingLoader() {
        new ErrorSilencingLoader();
    }
}

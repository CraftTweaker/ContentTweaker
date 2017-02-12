package com.teamacronymcoders.contenttweaker.proxies;

import com.teamacronymcoders.contenttweaker.renderers.ErrorSilencingLoader;

public class ClientProxy extends CommonProxy {
    @Override
    public void createErrorSilencingLoader() {
        new ErrorSilencingLoader();
    }
}

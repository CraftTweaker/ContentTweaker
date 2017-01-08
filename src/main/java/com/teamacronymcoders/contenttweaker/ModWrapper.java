package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.contenttweaker.api.IModWrapper;

public class ModWrapper implements IModWrapper {
    @Override
    public void logError(String error, Throwable throwable) {
        if(throwable == null) {
            ContentTweaker.instance.getLogger().error(error);
        } else {
            ContentTweaker.instance.getLogger().getLogger().error(error, throwable);
        }
    }
}

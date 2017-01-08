package com.teamacronymcoders.tailoredobjects;

import com.teamacronymcoders.tailoredobjects.api.IModWrapper;

public class ModWrapper implements IModWrapper {
    @Override
    public void logError(String error, Throwable throwable) {
        if(throwable == null) {
            TailoredObjects.instance.getLogger().error(error);
        } else {
            TailoredObjects.instance.getLogger().getLogger().error(error, throwable);
        }
    }
}

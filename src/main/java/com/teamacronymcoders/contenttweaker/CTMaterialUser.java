package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import crafttweaker.CraftTweakerAPI;

public class CTMaterialUser extends MaterialUser {
    public CTMaterialUser(IBaseMod mod) {
        super(mod);
    }

    public void logError(String message) {
        CraftTweakerAPI.logError(message);
        super.logError(message);
    }

    @Override
    protected boolean hasErred() {
        return super.hasErred() && ContentTweaker.scriptsSuccessful;
    }
}

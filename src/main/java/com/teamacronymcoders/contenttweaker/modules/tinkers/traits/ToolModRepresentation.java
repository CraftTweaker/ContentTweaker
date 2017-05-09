package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import com.teamacronymcoders.contenttweaker.modules.tinkers.functions.CanApplyTogetherEnchantmentFunction;
import com.teamacronymcoders.contenttweaker.modules.tinkers.functions.CanApplyTogetherToolModFunction;
import com.teamacronymcoders.contenttweaker.modules.tinkers.functions.GetExtraInfoFunction;
import stanhebben.zenscript.annotations.ZenMethod;

public class ToolModRepresentation {
    private String identifier;
    private String localizedName;
    private String LocalizedDesc;
    private GetExtraInfoFunction getExtraInfoFunction;
    private boolean isHidden;
    private CanApplyTogetherToolModFunction canApplyTogetherToolModFunction;
    private CanApplyTogetherEnchantmentFunction canApplyTogetherEnchantmentFunction;

    @ZenMethod
    public String getIdentifier() {
        return identifier;
    }

    @ZenMethod
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @ZenMethod
    public String getLocalizedName() {
        return localizedName;
    }

    @ZenMethod
    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    @ZenMethod
    public String getLocalizedDesc() {
        return LocalizedDesc;
    }

    @ZenMethod
    public void setLocalizedDesc(String localizedDesc) {
        LocalizedDesc = localizedDesc;
    }

    @ZenMethod
    public GetExtraInfoFunction getGetExtraInfoFunction() {
        return getExtraInfoFunction;
    }

    @ZenMethod
    public void setGetExtraInfoFunction(GetExtraInfoFunction getExtraInfoFunction) {
        this.getExtraInfoFunction = getExtraInfoFunction;
    }

    @ZenMethod
    public boolean isHidden() {
        return isHidden;
    }

    @ZenMethod
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @ZenMethod
    public CanApplyTogetherToolModFunction getCanApplyTogetherToolModFunction() {
        return canApplyTogetherToolModFunction;
    }

    @ZenMethod
    public void setCanApplyTogetherToolModFunction(CanApplyTogetherToolModFunction canApplyTogetherToolModFunction) {
        this.canApplyTogetherToolModFunction = canApplyTogetherToolModFunction;
    }

    @ZenMethod
    public CanApplyTogetherEnchantmentFunction getCanApplyTogetherEnchantmentFunction() {
        return canApplyTogetherEnchantmentFunction;
    }

    @ZenMethod
    public void setCanApplyTogetherEnchantmentFunction(CanApplyTogetherEnchantmentFunction canApplyTogetherEnchantmentFunction) {
        this.canApplyTogetherEnchantmentFunction = canApplyTogetherEnchantmentFunction;
    }
}

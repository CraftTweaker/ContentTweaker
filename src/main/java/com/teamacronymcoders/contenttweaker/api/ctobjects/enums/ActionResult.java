package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.EnumActionResult;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("mods.contenttweaker.ActionResult")
public class ActionResult implements ICTObject<EnumActionResult> {
    private EnumActionResult actionResult;

    private ActionResult(EnumActionResult actionResult) {
        this.actionResult = actionResult;
    }

    @ZenMethod
    public static ActionResult fail() {
        return new ActionResult(EnumActionResult.FAIL);
    }

    @ZenMethod
    public static ActionResult pass() {
        return new ActionResult(EnumActionResult.PASS);
    }

    @ZenMethod
    public static ActionResult success() {
        return new ActionResult(EnumActionResult.SUCCESS);
    }

    @ZenOperator(OperatorType.COMPARE)
    public boolean equals(ActionResult actionResult) {
        return this.getInternal().equals(actionResult.getInternal());
    }

    @Override
    public EnumActionResult getInternal() {
        return this.actionResult;
    }
}

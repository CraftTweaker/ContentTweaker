package com.teamacronymcoders.contenttweaker.api.ctobjects;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import net.minecraft.util.EnumActionResult;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;

public class ActionResult implements ICTObject<EnumActionResult> {
    private EnumActionResult actionResult;

    private ActionResult(EnumActionResult actionResult) {
        this.actionResult = actionResult;
    }

    @ZenGetter
    public static ActionResult fail() {
        return new ActionResult(EnumActionResult.FAIL);
    }

    @ZenGetter
    public static ActionResult pass() {
        return new ActionResult(EnumActionResult.PASS);
    }

    @ZenGetter
    public static ActionResult success() {
        return new ActionResult(EnumActionResult.SUCCESS);
    }

    @ZenOperator(OperatorType.EQUALS)
    public boolean equals(ActionResult actionResult) {
        return this.getInternal().equals(actionResult.getInternal());
    }

    @Override
    public EnumActionResult getInternal() {
        return this.actionResult;
    }
}

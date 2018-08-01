package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.material.EnumPushReaction;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("mods.contenttweaker.PushReaction")
@ZenRegister
public class PushReaction implements ICTObject<EnumPushReaction> {
    private EnumPushReaction pushReaction;

    private PushReaction(EnumPushReaction pushReaction) {
        this.pushReaction = pushReaction;
    }

    public static PushReaction of(EnumPushReaction mobilityFlag) {
        return new PushReaction(mobilityFlag);
    }

    @ZenMethod
    public static PushReaction normal() {
        return new PushReaction(EnumPushReaction.NORMAL);
    }

    @ZenMethod
    public static PushReaction destroy() {
        return new PushReaction(EnumPushReaction.DESTROY);
    }

    @ZenMethod
    public static PushReaction block() {
        return new PushReaction(EnumPushReaction.BLOCK);
    }

    @ZenMethod
    public static PushReaction ignore() {
        return new PushReaction(EnumPushReaction.IGNORE);
    }

    @ZenMethod
    public static PushReaction pushOnly() {
        return new PushReaction(EnumPushReaction.PUSH_ONLY);
    }

    @ZenOperator(OperatorType.COMPARE)
    public int compare(PushReaction other) {
        return this.getInternal().compareTo(other.getInternal());
    }
    @Override
    public EnumPushReaction getInternal() {
        return pushReaction;
    }


}

package com.teamacronymcoders.contenttweaker.api.ctobjects;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.EnumFacing;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenRegister
@ZenClass("mods.contenttweaker.Facing")
public class Facing implements ICTObject<EnumFacing> {
    private EnumFacing facing;

    private Facing(EnumFacing enumFacing) {
        this.facing = enumFacing;
    }

    @ZenOperator(OperatorType.EQUALS)
    public boolean equals(Facing facing) {
        return this.getInternal() == facing.getInternal();
    }

    @ZenGetter
    public static Facing north() {
        return new Facing(EnumFacing.NORTH);
    }

    @ZenGetter
    public static Facing east() {
        return  new Facing(EnumFacing.EAST);
    }

    @ZenGetter
    public static Facing south() {
        return new Facing(EnumFacing.SOUTH);
    }

    @ZenGetter
    public static Facing west() {
        return new Facing(EnumFacing.WEST);
    }

    @ZenGetter
    public static Facing down() {
        return new Facing(EnumFacing.DOWN);
    }

    @ZenGetter
    public static Facing up() {
        return new Facing(EnumFacing.UP);
    }

    @Override
    public EnumFacing getInternal() {
        return this.facing;
    }
}

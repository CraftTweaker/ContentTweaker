package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.EnumFacing;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

import java.util.Objects;

@ZenRegister
@ZenClass("mods.contenttweaker.Facing")
public class Facing implements ICTObject<EnumFacing> {
    private EnumFacing facing;

    private Facing(EnumFacing enumFacing) {
        this.facing = enumFacing;
    }

    public static Facing of(EnumFacing enumFacing) {
        return new Facing(enumFacing);
    }

    @ZenOperator(OperatorType.COMPARE)
    public int compare(Facing other) {
        return this.getInternal().compareTo(other.getInternal());
    }

    @ZenMethod
    public static Facing north() {
        return new Facing(EnumFacing.NORTH);
    }

    @ZenMethod
    public static Facing east() {
        return new Facing(EnumFacing.EAST);
    }

    @ZenMethod
    public static Facing south() {
        return new Facing(EnumFacing.SOUTH);
    }

    @ZenMethod
    public static Facing west() {
        return new Facing(EnumFacing.WEST);
    }

    @ZenMethod
    public static Facing down() {
        return new Facing(EnumFacing.DOWN);
    }

    @ZenMethod
    public static Facing up() {
        return new Facing(EnumFacing.UP);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Facing && Objects.equals(this.getInternal(), ((Facing) obj).getInternal());
    }

    @Override
    public EnumFacing getInternal() {
        return this.facing;
    }
}

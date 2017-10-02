package com.teamacronymcoders.contenttweaker.api.ctobjects.aabb;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.math.AxisAlignedBB;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("mods.contenttweaker.aabb")
public class MCAxisAlignedBB implements ICTObject<AxisAlignedBB> {
    @ZenProperty
    public double minX = 0.0;
    @ZenProperty
    public double minY = 0.0;
    @ZenProperty
    public double minZ = 0.0;
    @ZenProperty
    public double maxX = 1.0;
    @ZenProperty
    public double maxY = 1.0;
    @ZenProperty
    public double maxZ = 1.0;

    public MCAxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @ZenMethod
    public static MCAxisAlignedBB create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new MCAxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @ZenMethod
    public double getMinX() {
        return minX;
    }

    @ZenMethod
    public void setMinX(double minX) {
        this.minX = minX;
    }

    @ZenMethod
    public double getMinY() {
        return minY;
    }

    @ZenMethod
    public void setMinY(double minY) {
        this.minY = minY;
    }

    @ZenMethod
    public double getMinZ() {
        return minZ;
    }

    @ZenMethod
    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    @ZenMethod
    public double getMaxX() {
        return maxX;
    }

    @ZenMethod
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    @ZenMethod
    public double getMaxY() {
        return maxY;
    }

    @ZenMethod
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    @ZenMethod
    public double getMaxZ() {
        return maxZ;
    }

    @ZenMethod
    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    @Override
    public AxisAlignedBB getInternal() {
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

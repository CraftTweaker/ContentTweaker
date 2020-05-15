package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.contenttweaker.blocks.wrappers.MCAxisAlignedBB;
import com.blamejared.contenttweaker.blocks.wrappers.MCVoxelShape;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker.impl.util.MCDirectionAxis;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCVoxelShape")
@Document("mods/contenttweaker/block/MCVoxelShape")
@ZenWrapper(wrappedClass = "net.minecraft.util.math.shapes.VoxelShape", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCVoxelShape {
    private final VoxelShape internal;

    public MCVoxelShape(VoxelShape internal){
        this.internal = internal;
    }

    public VoxelShape getInternal() {
        return this.internal;
    }

    /**
     * "Projects" this shape onto the given side. For each box in the shape, if it does not touch the given side, it is
     * eliminated. Otherwise, the box is extended in the given axis to cover the entire range [0, 1].
     */
    @ZenCodeType.Method
    public MCVoxelShape project(MCDirection side) {
        return new MCVoxelShape(internal.project((side).getInternal()));
    }


    @ZenCodeType.Method
    public MCVoxelShape withOffset(double xOffset, double yOffset, double zOffset) {
        return new MCVoxelShape(internal.withOffset(xOffset, yOffset, zOffset));
    }


    @ZenCodeType.Method
    public String toString() {
        return (internal.toString());
    }


    @ZenCodeType.Method
    public boolean isEmpty() {
        return internal.isEmpty();
    }


    @ZenCodeType.Method
    public double max(MCDirectionAxis p_197760_1_, double p_197760_2_, double p_197760_4_) {
        return internal.max((p_197760_1_).getInternal(), p_197760_2_, p_197760_4_);
    }


    @ZenCodeType.Method
    public double getStart(MCDirectionAxis axis) {
        return internal.getStart((axis).getInternal());
    }


    @ZenCodeType.Method
    public MCVoxelShape simplify() {
        return new MCVoxelShape(internal.simplify());
    }


    @ZenCodeType.Method
    public double getAllowedOffset(MCDirectionAxis movementAxis, MCAxisAlignedBB collisionBox, double desiredOffset) {
        return internal.getAllowedOffset((movementAxis).getInternal(), (collisionBox).getInternal(), desiredOffset);
    }


    @ZenCodeType.Method
    public double getEnd(MCDirectionAxis axis) {
        return internal.getEnd((axis).getInternal());
    }


    @ZenCodeType.Method
    public List<MCAxisAlignedBB> toBoundingBoxList() {
        return (internal.toBoundingBoxList()).stream().map(myStrangeTypeMCAxisAlignedBB -> new MCAxisAlignedBB(myStrangeTypeMCAxisAlignedBB)).collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }


    @ZenCodeType.Method
    public double min(MCDirectionAxis axis, double p_197764_2_, double p_197764_4_) {
        return internal.min((axis).getInternal(), p_197764_2_, p_197764_4_);
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB getBoundingBox() {
        return new MCAxisAlignedBB(internal.getBoundingBox());
    }


}

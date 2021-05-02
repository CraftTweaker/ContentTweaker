package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.state.BlockFaceShape;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenRegister
@ZenClass("mods.contenttweaker.FaceShape")
public class FaceShape implements ICTObject<BlockFaceShape> {
    private BlockFaceShape faceShape;

    private FaceShape(BlockFaceShape blockFaceShape) {
        this.faceShape = blockFaceShape;
    }

    public static FaceShape of(BlockFaceShape blockFaceShape) {
        return new FaceShape(blockFaceShape);
    }

    @ZenOperator(OperatorType.COMPARE)
    public int compare(FaceShape other) {
        return this.getInternal().compareTo(other.getInternal());
    }

    @ZenMethod
    public static FaceShape solid() {
        return new FaceShape(BlockFaceShape.SOLID);
    }

    @ZenMethod
    public static FaceShape bowl() {
        return  new FaceShape(BlockFaceShape.BOWL);
    }

    @ZenMethod
    public static FaceShape center_small() {
        return new FaceShape(BlockFaceShape.CENTER_SMALL);
    }

    @ZenMethod
    public static FaceShape middle_pole_thin() {
        return new FaceShape(BlockFaceShape.MIDDLE_POLE_THIN);
    }

    @ZenMethod
    public static FaceShape center() {
        return new FaceShape(BlockFaceShape.CENTER);
    }

    @ZenMethod
    public static FaceShape middle_pole() {
        return new FaceShape(BlockFaceShape.MIDDLE_POLE);
    }

    @ZenMethod
    public static FaceShape center_big() {
        return new FaceShape(BlockFaceShape.CENTER_BIG);
    }

    @ZenMethod
    public static FaceShape middle_pole_thick() {
        return new FaceShape(BlockFaceShape.MIDDLE_POLE_THICK);
    }

    @ZenMethod
    public static FaceShape undefined() {
        return new FaceShape(BlockFaceShape.UNDEFINED);
    }

    @Override
    public BlockFaceShape getInternal() {
        return this.faceShape;
    }
}

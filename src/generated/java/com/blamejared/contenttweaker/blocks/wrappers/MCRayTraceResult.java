package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.contenttweaker.blocks.wrappers.MCRayTraceResultType;
import com.blamejared.contenttweaker.blocks.wrappers.MCVec3d;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCRayTraceResult")
@Document("mods/contenttweaker/block/MCRayTraceResult")
@ZenWrapper(wrappedClass = "net.minecraft.util.math.RayTraceResult", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCRayTraceResult {
    private final RayTraceResult internal;

    public MCRayTraceResult(RayTraceResult internal){
        this.internal = internal;
    }

    public RayTraceResult getInternal() {
        return this.internal;
    }

    /**
     * Returns the hit position of the raycast, in absolute world coordinates
     */
    @ZenCodeType.Method
    public MCVec3d getHitVec() {
        return new MCVec3d(internal.getHitVec());
    }


    @ZenCodeType.Method
    public MCRayTraceResultType getType() {
        return new MCRayTraceResultType(internal.getType());
    }


}

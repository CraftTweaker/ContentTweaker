package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.math.RayTraceResult.Type;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCRayTraceResultType")
@Document("mods/contenttweaker/block/MCRayTraceResultType")
@ZenWrapper(wrappedClass = "net.minecraft.util.math.RayTraceResult.Type", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCRayTraceResultType {
    private final Type internal;

    public MCRayTraceResultType(Type internal){
        this.internal = internal;
    }

    public Type getInternal() {
        return this.internal;
    }

}

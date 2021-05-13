package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemColorSupplier")
@Document("mods/contenttweaker/API/functions/IItemColorSupplier")
public interface IItemColorSupplier extends ICotFunction {
    IItemColorSupplier DEFAULT = ((stack, tintIndex) -> -1);

    @ZenCodeType.Method
    int apply(IItemStack stack, int tintIndex);
}

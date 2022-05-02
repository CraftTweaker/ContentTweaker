package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUseDurationSupplier")
@Document("mods/contenttweaker/API/functions/IItemUseDurationSupplier")
public interface IItemUseDurationSupplier extends ICotFunction {
    @ZenCodeType.Method
    int apply(IItemStack stack);
}

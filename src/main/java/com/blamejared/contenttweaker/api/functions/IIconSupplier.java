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
@ZenCodeType.Name("mods.contenttweaker.functions.IIconSupplier")
@Document("mods/contenttweaker/API/functions/IIconSupplier")
public interface IIconSupplier extends ICotFunction {
    @ZenCodeType.Method
    IItemStack supply();
}

package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.UseAction;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUseActionSupplier")
@Document("mods/contenttweaker/API/functions/IItemUseActionSupplier")
public interface IItemUseActionSupplier extends ICotFunction {
    @ZenCodeType.Method
    UseAction apply(IItemStack stack);
}

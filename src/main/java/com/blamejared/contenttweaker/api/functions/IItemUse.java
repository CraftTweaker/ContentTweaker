package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUse")
@Document("mods/contenttweaker/API/functions/IItemUse")
public interface IItemUse extends ICotFunction {
    @ZenCodeType.Method
    ActionResultType apply(ItemUseContext context);
}

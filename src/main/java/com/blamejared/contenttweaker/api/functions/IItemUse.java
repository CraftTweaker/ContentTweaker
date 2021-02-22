package com.blamejared.contenttweaker.api.functions;

import com.blamejared.contenttweaker.wrappers.MCItemUseContext;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IItemUse")
@Document("mods/contenttweaker/API/functions/IItemUse")
public interface IItemUse {
    @ZenCodeType.Method
    String apply(MCItemUseContext context);
}

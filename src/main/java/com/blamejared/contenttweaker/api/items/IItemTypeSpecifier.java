package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
//@FunctionalInterface //Does no FunctionalInterface fix ZC?
@ZenCodeType.Name("mods.contenttweaker.item.IItemTypeSpecifier")
public interface IItemTypeSpecifier<T extends IIsBuilder> {
    T apply(ItemBuilder builder);
}

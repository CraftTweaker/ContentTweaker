package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IColorSupplier")
public interface IColorSupplier {
    CTColor getColor(String identifier);
}

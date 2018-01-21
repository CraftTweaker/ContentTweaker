package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.resourcelocation.CTResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IResourceLocationSupplier")
public interface IResourceLocationSupplier {
    CTResourceLocation getResourceLocation(String identifier);
}

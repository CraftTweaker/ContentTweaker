package com.teamacronymcoders.contenttweaker.modules.materials.functions;

import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.MaterialPartItemStackSupplier")
public interface IMaterialPartItemStackSupplier {
    IItemStack getItemStack(IMaterialPart materialPart);
}

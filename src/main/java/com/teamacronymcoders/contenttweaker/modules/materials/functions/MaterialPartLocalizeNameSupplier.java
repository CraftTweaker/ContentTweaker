package com.teamacronymcoders.contenttweaker.modules.materials.functions;

import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.ILocalizedNameSupplier;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.MaterialPartLocalizedNameSupplier")
public class MaterialPartLocalizeNameSupplier implements ILocalizedNameSupplier {
    private String name;

    public MaterialPartLocalizeNameSupplier(IMaterialPart materialPart) {
        this.name = materialPart.getLocalizedName();
    }

    @ZenMethod
    public static MaterialPartLocalizeNameSupplier create(IMaterialPart materialPart) {
        return new MaterialPartLocalizeNameSupplier(materialPart);
    }

    @Override
    public String getLocalizedName(IItemStack itemStack) {
        return this.name;
    }
}

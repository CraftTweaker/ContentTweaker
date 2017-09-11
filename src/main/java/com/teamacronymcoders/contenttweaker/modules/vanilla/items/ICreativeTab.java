package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.CreativeTab")
public interface ICreativeTab extends ICTObject<CreativeTabs> {
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenSetter("unlocalizedName")
    void setUnlocalizedName(String unlocalizedName);

    @ZenGetter("iconStack")
    IItemStack getIconStack();

    @ZenSetter("iconStack")
    void setIconStack(IItemStack iconStack);

    @ZenMethod
    void register();
}

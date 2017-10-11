package com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack;

import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player.ICTPlayer;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.MutableItemStack")
public interface IMutableItemStack extends IItemStack {
    void setCount(int count);

    void shrink(int count);

    void grow(int count);

    void damage(int amount, ICTPlayer player);
}

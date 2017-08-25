package com.teamacronymcoders.contenttweaker.modules.vanilla.events;

import com.google.common.collect.Lists;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.contenttweaker.VanillaEvents")
public class Events {
    private static List<OnItemCraft> onItemCraftFunctions = Lists.newArrayList();

    @ZenMethod
    public static void addItemCraftFunction(OnItemCraft onItemCraft) {
        onItemCraftFunctions.add(onItemCraft);
    }

    public static List<OnItemCraft> getOnItemCraftFunctions() {
        return onItemCraftFunctions;
    }
}

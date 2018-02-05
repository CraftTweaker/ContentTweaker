package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import net.minecraft.creativetab.CreativeTabs;

@BracketHandler(priority = 11)
public class CreativeTabBracketHandler extends ResourceBracketHandler {
    public CreativeTabBracketHandler() {
        super("CreativeTab", CreativeTabBracketHandler.class);
    }

    public static ICreativeTab getCreativeTab(String name) {
        CreativeTabs creativeTab = ContentTweakerAPI.getInstance().getCreativeTabs().getResource(name);
        if (creativeTab == null) {
            CraftTweakerAPI.logError("Could not find Material for name: " + name);
        }
        return new MCCreativeTab(creativeTab);
    }
}

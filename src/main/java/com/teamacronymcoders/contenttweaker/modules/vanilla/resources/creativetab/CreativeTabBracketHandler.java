package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import minetweaker.MineTweakerAPI;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabBracketHandler extends ResourceBracketHandler {
    public CreativeTabBracketHandler() {
        super("CreativeTab", CreativeTabBracketHandler.class);
    }

    public static ICreativeTabDefinition getCreativeTab(String name) {
        CreativeTabs creativeTab = ContentTweakerAPI.getInstance().getCreativeTabs().getResource(name);
        if (creativeTab == null) {
            MineTweakerAPI.logError("Could not find Material for name: " + name);
        }
        return new CreativeTabDefinition(creativeTab);
    }
}

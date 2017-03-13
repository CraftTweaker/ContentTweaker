package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabDefinition implements ICreativeTabDefinition {
    private CreativeTabs creativeTab;

    public CreativeTabDefinition(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    @Override
    public Object getInternal() {
        return this.creativeTab;
    }
}

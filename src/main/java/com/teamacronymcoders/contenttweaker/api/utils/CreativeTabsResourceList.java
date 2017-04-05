package com.teamacronymcoders.contenttweaker.api.utils;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CreativeTabsResourceList extends ResourceList<CreativeTabs> {
    public CreativeTabsResourceList() {
        super(CreativeTabs.class, new ArrayList<>());
    }

    public void addResource(String name, CreativeTabs resource) {
        //They're already added when Created.
    }

    public CreativeTabs getResource(String name) {
        CreativeTabs resourceCreativeTab = null;
        CreativeTabs[] creativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY;

        Iterator<CreativeTabs> creativeTabsIterable = Arrays.stream(creativeTabs).iterator();
        while (creativeTabsIterable.hasNext() && resourceCreativeTab == null) {
            CreativeTabs creativeTab = creativeTabsIterable.next();
            String tabLabel = ReflectionHelper.getPrivateValue(CreativeTabs.class, creativeTab, "tabLabel", "field_78034_o");
            //TODO REMOVE
            ContentTweaker.instance.getLogger().warning(tabLabel);
            if (name.equalsIgnoreCase(tabLabel)) {
                resourceCreativeTab = creativeTab;
            }
        }

        return resourceCreativeTab;
    }
}

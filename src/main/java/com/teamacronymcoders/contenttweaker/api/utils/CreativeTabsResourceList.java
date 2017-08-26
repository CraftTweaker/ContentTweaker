package com.teamacronymcoders.contenttweaker.api.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.*;
import java.util.stream.Collectors;

public class CreativeTabsResourceList extends ResourceList<CreativeTabs> {
    public CreativeTabsResourceList() {
        super(CreativeTabs.class, new ArrayList<>());
    }

    @Override
    public void addResource(String name, CreativeTabs resource) {
        //They're already added when Created.
    }

    @Override
    public CreativeTabs getResource(String name) {
        CreativeTabs resourceCreativeTab = null;
        CreativeTabs[] creativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY;

        Iterator<CreativeTabs> creativeTabsIterable = Arrays.stream(creativeTabs).iterator();
        while (creativeTabsIterable.hasNext() && resourceCreativeTab == null) {
            CreativeTabs creativeTab = creativeTabsIterable.next();
            String tabLabel = ReflectionHelper.getPrivateValue(CreativeTabs.class, creativeTab, "tabLabel", "field_78034_o");
            if (name.equalsIgnoreCase(tabLabel)) {
                resourceCreativeTab = creativeTab;
            }
        }

        return resourceCreativeTab;
    }

    @Override
    public Collection<CreativeTabs> getAllResources() {
        return Arrays.asList(CreativeTabs.CREATIVE_TAB_ARRAY);
    }

    @Override
    public Collection<String> getAllNames() {
        return Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY).map(creativeTab -> {
            @SuppressWarnings("UnnecessaryLocalVariable")
            String label = ReflectionHelper.getPrivateValue(
                    CreativeTabs.class, creativeTab, "tabLabel", "field_78034_o");
            return label;
        }).collect(Collectors.toList());
    }
}

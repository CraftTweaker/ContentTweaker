package com.teamacronymcoders.contenttweaker.modules.crt.classes;

import minetweaker.*;
import minetweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.*;

@ZenClass("mods.content.Tabs")
public class MCCreativeTab  {
	
	private static Map<String, CreativeTabs> tabs = new HashMap<>();
	
	public static void init(){
		for(CreativeTabs creativeTabs : CreativeTabs.CREATIVE_TAB_ARRAY) {
			tabs.put(creativeTabs.getTabLabel(), creativeTabs);
		}
	}
	@ZenMethod
	public static void create(String name, IItemStack displayItem) {
		MineTweakerAPI.apply(new IUndoableAction() {
			@Override
			public void apply() {
				CreativeTabs newTab = new CreativeTabs(name) {
					@Override
					public Item getTabIconItem() {
						return ((ItemStack)displayItem.getInternal()).getItem();
					}
				};
				tabs.put(name, newTab);
			}
			
			@Override
			public boolean canUndo() {
				return false;
			}
			
			@Override
			public void undo() {
				
			}
			
			@Override
			public String describe() {
				return "Adding Creative Tab - " + name;
			}
			
			@Override
			public String describeUndo() {
				return null;
			}
			
			@Override
			public Object getOverrideKey() {
				return null;
			}
		});
	}
	
	@ZenGetter
	public static CreativeTabs getCreativeTab(String name){
		return tabs.getOrDefault(name, CreativeTabs.SEARCH);
	}
}

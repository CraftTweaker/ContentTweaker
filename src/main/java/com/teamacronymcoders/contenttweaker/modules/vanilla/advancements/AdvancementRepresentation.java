package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent.TextComponents;
import crafttweaker.api.item.IItemStack;

import net.minecraft.advancements.Advancement;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.*;

@ZenClass
public class AdvancementRepresentation implements IRepresentation<Advancement>{

	@ZenProperty
	public IItemStack icon;

	@ZenProperty
	public TextComponents title;

	@ZenProperty
	public String frame;

	@ZenProperty
	public String background;

	@ZenProperty
	public TextComponents description;

	@ZenProperty
	public Boolean toast;

	@ZenProperty
	public Boolean announce;

	@ZenProperty
	public Boolean hidden;

	@ZenProperty
	public String parent;

	@ZenProperty
	public String criteriaName;

	@ZenProperty
	public String trigger;

	@ZenProperty
	public String condition;

	@ZenProperty
	public ArrayList<String> requirements;

	@ZenProperty
	public List<String> recipes;

	@ZenProperty
	public List<IItemStack> loot;

	@ZenProperty
	public int xp;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public void register() {

	}

	@Override
	public Advancement getInternal() {
		return null;
	}

	//Functions
}

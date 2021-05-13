package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.extensions.IForgeItem;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Item. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 *
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via cotItem BEP.
 */
@ZenRegister
@Document("mods/contenttweaker/API/item/IIsCotItem")
@ZenCodeType.Name("mods.contenttweaker.item.IIsCotItem")
public interface IIsCotItem extends IHasResourceLocation, IHasResourcesToWrite, IForgeItem {
}

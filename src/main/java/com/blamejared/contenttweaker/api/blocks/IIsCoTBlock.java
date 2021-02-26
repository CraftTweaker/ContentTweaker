package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.extensions.*;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Block. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 *
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via cotItem BEP.
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/IIsCotBlock")
@ZenCodeType.Name("mods.contenttweaker.block.IIsCotBlock")
public interface IIsCoTBlock extends IHasCoTItem, IHasResourcesToWrite, IHasResourceLocation, IForgeBlock {

}

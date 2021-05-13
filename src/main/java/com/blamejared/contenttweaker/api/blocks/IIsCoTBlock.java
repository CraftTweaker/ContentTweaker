package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.api.IHasCoTItem;
import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface IIsCoTBlock extends IHasCoTItem, IHasResourcesToWrite, IHasResourceLocation, IForgeBlock {
}

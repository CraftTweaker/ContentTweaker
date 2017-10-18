package com.teamacronymcoders.contenttweaker.api.ctobjects.entity.living;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import net.minecraft.entity.EntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.EntityLiving")
public interface IEntityLiving<T extends EntityLivingBase> extends ICTObject<T> {

}

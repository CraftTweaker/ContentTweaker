package com.blamejared.contenttweaker.native_type;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.ActionResultType;

@ZenRegister
@Document("mods/contenttweaker/API/util/ActionResultType")
@NativeTypeRegistration(value = ActionResultType.class, zenCodeName = "mods.contenttweaker.util.ActionResultType")
public class ExpandActionResultType {
}

package com.blamejared.contenttweaker.native_type;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.Hand;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/contenttweaker/API/util/MCHand")
@NativeTypeRegistration(value = Hand.class, zenCodeName = "mods.contenttweaker.MCHand")
public class ExpandHand {
    @ZenCodeType.Caster(implicit = true)
    public String getName(Hand internal) {
        return internal.name();
    }
}

package com.blamejared.contenttweaker.native_type;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

@ZenRegister
@Document("mods/contenttweaker/API/util/Random")
@NativeTypeRegistration(value = Random.class, zenCodeName = "mods.contenttweaker.util.Random")
public class ExpandRandom {
    @ZenCodeType.Method
    public static boolean nextBoolean(Random internal) {
        return internal.nextBoolean();
    }

    @ZenCodeType.Method
    public static int nextInt(Random internal, int bound) {
        return internal.nextInt(bound);
    }
}

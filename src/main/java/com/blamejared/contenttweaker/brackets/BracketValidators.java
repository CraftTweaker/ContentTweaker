package com.blamejared.contenttweaker.brackets;

import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.api.BracketValidators")
public class BracketValidators {
    
    @ZenCodeType.Method
    @BracketValidator("itemgroup")
    public static boolean validateItemgroupBracket(String tokens) {
        return Arrays.stream(ItemGroup.GROUPS).anyMatch(group -> group.getPath().equals(tokens));
    }
}

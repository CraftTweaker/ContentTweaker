package com.blamejared.contenttweaker.brackets;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@Document("mods/contenttweaker/API/BracketValidators")
@ZenCodeType.Name("mods.contenttweaker.api.BracketValidators")
public class BracketValidators {
    
    @ZenCodeType.Method
    @BracketValidator("itemgroup")
    public static boolean validateItemGroupBracket(String tokens) {
        return Arrays.stream(ItemGroup.GROUPS).anyMatch(group -> group.getPath().equals(tokens));
    }
}

package com.blamejared.contenttweaker.brackets;

import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("mods/contenttweaker/API/BracketDumpers")
@ZenCodeType.Name("mods.contenttweaker.api.BracketDumpers")
public class BracketDumpers {
    
    @ZenCodeType.Method
    @BracketDumper(value = "itemgroup")
    public static Collection<String> getItemGroupBracketDump() {
        return Arrays.stream(ItemGroup.GROUPS).map(itemGroup -> new MCItemGroup(itemGroup).getCommandString()).collect(Collectors.toList());
    }
}

package com.blamejared.contenttweaker.brackets;

import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.api.BracketDumpers")
public class BracketDumpers {
    
    @BracketDumper(value = "itemGroup")
    public static Collection<String> getItemgroupBracketDump() {
        return Arrays.stream(ItemGroup.GROUPS)
                .map(itemGroup -> new MCItemGroup(itemGroup).getCommandString())
                .collect(Collectors.toList());
    }
}

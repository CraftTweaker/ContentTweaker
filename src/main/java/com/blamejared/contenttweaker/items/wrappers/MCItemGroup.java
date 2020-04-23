package com.blamejared.contenttweaker.items.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemGroup")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemGroup", displayStringFormat = "%s.getCommandString()")
public class MCItemGroup implements CommandStringDisplayable {
    
    private final ItemGroup internal;
    
    public MCItemGroup(ItemGroup internal) {
        this.internal = internal;
    }
    
    public ItemGroup getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public String getPath() {
        return internal.getPath();
    }
    
    @ZenCodeType.Getter("path")
    public String getPathGetter() {
        return getPath();
    }
    
    @ZenCodeType.Method
    public MCItemGroup setBackgroundImageName(String texture) {
        final ItemGroup itemGroup = internal.setBackgroundImageName(texture);
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    @ZenCodeType.Method
    public MCItemGroup setTabPath(String pathIn) {
        final ItemGroup itemGroup = internal.setTabPath(pathIn);
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    
    @ZenCodeType.Method
    public MCItemGroup setNoTitle() {
        final ItemGroup itemGroup = internal.setNoTitle();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    @ZenCodeType.Method
    public MCItemGroup setNoScrollbar() {
        final ItemGroup itemGroup = internal.setNoScrollbar();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    @Override
    public String toString() {
        return internal.toString();
    }
    
    @Override
    public String getCommandString() {
        return "<itemgroup:" + getPath() + ">";
    }
}

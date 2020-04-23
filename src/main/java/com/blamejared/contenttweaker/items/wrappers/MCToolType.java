package com.blamejared.contenttweaker.items.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCToolType")
@ZenWrapper(wrappedClass = "net.minecraftforge.common.ToolType", displayStringFormat = "%s.getCommandString()")
public class MCToolType implements CommandStringDisplayable {
    
    private final ToolType internal;
    
    public MCToolType(ToolType internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCToolType(String name){
        this(ToolType.get(name));
    }
    
    public ToolType getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter
    public String getName() {
        return internal.getName();
    }
    
    @Override
    public String getCommandString() {
        return "<tooltype:" + getName() + ">";
    }
    
    @Override
    @ZenCodeType.Caster
    @ZenCodeType.Method
    public String toString() {
        return internal.toString();
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof MCToolType))
            return false;
        
        MCToolType that = (MCToolType) o;
    
        return internal.equals(that.internal);
    }
    
    @ZenCodeType.Method
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}

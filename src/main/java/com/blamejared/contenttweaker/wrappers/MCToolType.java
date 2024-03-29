package com.blamejared.contenttweaker.wrappers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

// TODO remove this next breaking change - now merged into CraftTweaker itself
/**
 * A ToolType is used to identify what kind of blocks a tool can mine,
 * or inversely, what kind of tool is required to mine a given block.
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCToolType")
@ZenWrapper(wrappedClass = "net.minecraftforge.common.ToolType", displayStringFormat = "%s.getCommandString()")
public class MCToolType implements CommandStringDisplayable {
    
    private final ToolType internal;
    
    public MCToolType(ToolType internal) {
        this.internal = internal;
        // If someone is using this class, tell them to stop
        CraftTweakerAPI.logWarning("MCToolType is now part of CraftTweaker, you should only be using `crafttweaker.api.tool.ToolType` instead!");
    }
    
    /**
     * Constructs a ToolType object.
     * If one with the given name already exists, they will internally point to the same toolType.
     * Otherwise, a new one with the name is created (The same holds true for Brackets as well!)
     * @param name The name to be used
     * @docParam name "pickaxe"
     */
    @ZenCodeType.Constructor
    public MCToolType(String name){
        this(ToolType.get(name));
    }
    
    public ToolType getInternal() {
        return internal;
    }
    
    /**
     * Gets the name of this toolType.
     * The name is what is used in the Bracket expression after the `<toolType:`
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public String getName() {
        return internal.getName();
    }
    
    @Override
    public String getCommandString() {
        return "<toolType:" + getName() + ">";
    }
    
    /**
     * Get the string representation of this type.
     * Is different from commandString!
     */
    @Override
    @ZenCodeType.Caster
    @ZenCodeType.Method
    public String toString() {
        return internal.toString();
    }
    
    /**
     * Compares if two given MCToolType objects are equal
     *
     * @param o The other object
     * @docParam o new MCToolType("pickaxe")
     */
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
    
    /**
     * Returns the object's hash code
     */
    @ZenCodeType.Method
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public ToolType asToolType(){
        return internal;
    }
    
}

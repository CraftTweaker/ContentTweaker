package com.blamejared.contenttweaker.zencode;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;

@ZenRegister
@ZenCodeType.Expansion("int .. int")
public class ExpandIntRange {
    
    private static Field from;
    private static Field to;
    
    public static void initFields(Object _this) {
        Field fromField = null, toField = null;
        try {
            final Class<?> aClass = _this.getClass();
            fromField = aClass.getField("from");
            toField = aClass.getField("to");
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        }
        from = fromField;
        to = toField;
    }
    
    @ZenCodeType.Method
    public static CoTIntRange toCoTIntRange(Object _this) {
        return asCoTIntRange(_this);
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static CoTIntRange asCoTIntRange(Object _this) {
        if(from == null || to == null || from.getDeclaringClass() != _this.getClass()) {
            initFields(_this);
        }
        
        if(from == null || to == null) {
            throw new IllegalStateException("Report to the Author!\nCould not get from and to fields for " + _this);
        }
        
        try {
            return new CoTIntRange(from.getInt(_this), to.getInt(_this));
        } catch(IllegalAccessException e) {
            throw new IllegalStateException("Report to the Author!\nCould not get from and to fields for " + _this, e);
        }
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static IndexList asIndexList(Object _this) {
        return new IndexList(asCoTIntRange(_this));
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static SlotSelector asSlotSelector(Object _this) {
        return asIndexList(_this);
    }
}

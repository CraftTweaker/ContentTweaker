package com.blamejared.contenttweaker.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.state.*;
import net.minecraft.state.properties.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCBlockStateProperty")
@Document("mods/contenttweaker/block/MCBlockStateProperty")
public class MCBlockStateProperty implements CommandStringDisplayable {
    
    private final Property<?> internal;
    private final String fieldName;
    
    public MCBlockStateProperty(Property<?> internal, String fieldName) {
        this.internal = internal;
        this.fieldName = fieldName;
    }
    
    public static MCBlockStateProperty getFromString(String name) {
        //TODO: Custom properties as well?!?
        final Class<BlockStateProperties> blockStatePropertiesClass = BlockStateProperties.class;
        for(Field field : blockStatePropertiesClass.getFields()) {
            if(field.getName().equalsIgnoreCase(name)) {
                try {
                    return new MCBlockStateProperty((Property<?>) field.get(null), field.getName());
                } catch(IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public IProperty<?> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public String getName() {
        return internal.getName();
    }
    
    @Override
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object other) {
        return other instanceof MCBlockStateProperty && internal.equals(((MCBlockStateProperty) other).internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allowedValues")
    public List<String> getAllowedValues() {
        return internal.getAllowedValues()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fieldName")
    public String getFieldName() {
        return fieldName;
    }
    
    @Override
    public String getCommandString() {
        return "<blockstateproperty:" + fieldName + ">";
    }
}

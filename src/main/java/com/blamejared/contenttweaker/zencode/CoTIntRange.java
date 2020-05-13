package com.blamejared.contenttweaker.zencode;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.api.CoTIntRange")
public final class CoTIntRange implements CommandStringDisplayable, Iterable<Integer> {
    
    public final int from;
    public final int to;
    
    public CoTIntRange(int from, int to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public String getCommandString() {
        return toString();
    }
    
    @Override
    public String toString() {
        return from + " .. " + to;
    }
    
    public IntStream stream() {
        return IntStream.range(from, to);
    }
    
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }
}

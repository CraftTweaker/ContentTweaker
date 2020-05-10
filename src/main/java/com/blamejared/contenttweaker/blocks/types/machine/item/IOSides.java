package com.blamejared.contenttweaker.blocks.types.machine.item;

import com.blamejared.crafttweaker.impl.util.*;

import java.util.*;

public class IOSides {
    private final Set<MCDirection> inputSides;
    private final Set<MCDirection> outputSides;
    private final Set<MCDirection> pullInputSides;
    private final Set<MCDirection> pushOutputs;
    
    public IOSides() {
        this.inputSides = new HashSet<>();
        this.pullInputSides = new HashSet<>();
        this.outputSides = new HashSet<>();
        this.pushOutputs = new HashSet<>();
    }
    
    public void setInputOnSide(MCDirection side) {
        inputSides.add(side);
    }
    
    public boolean isInputOnSide(MCDirection direction) {
        return inputSides.contains(direction);
    }
    
    public void setPullFromSide(MCDirection side) {
        pullInputSides.add(side);
    }
    
    public boolean doesPullFromSide(MCDirection direction) {
        return pullInputSides.contains(direction);
    }
    
    
    public void setOutputOnSide(MCDirection side) {
        outputSides.add(side);
    }
    
    public boolean isOutputOnSide(MCDirection side) {
        return outputSides.contains(side);
    }
    
    public void setPushToSide(MCDirection side) {
        pushOutputs.add(side);
    }
    
    public boolean doesPushToSide(MCDirection side) {
        return pushOutputs.contains(side);
    }
    
    public void addInputSidesTo(Set<MCDirection> directions){
        directions.addAll(inputSides);
    }
    
    public void addOutputSidesTo(Set<MCDirection> directions){
        directions.addAll(inputSides);
    }
}

package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.item.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.gui.*;
import com.blamejared.crafttweaker.impl.util.*;

import java.util.*;

public class ItemSlot {
    private final GroupNames groupNames;
    private final IOSides ioSides;
    private final int index;
    private final ItemSlotGuiInformation guiInformation;
    
    public ItemSlot(int index, GroupNames groupNames, IOSides ioSides, ItemSlotGuiInformation guiInformation) {
        this.groupNames = groupNames;
        this.ioSides = ioSides;
        this.index = index;
        this.guiInformation = guiInformation;
    }
    
    public void addInputSidesTo(Set<MCDirection> directions){
        ioSides.addInputSidesTo(directions);
    }
    
    public void addOutputSidesTo(Set<MCDirection> directions){
        ioSides.addOutputSidesTo(directions);
    }
}

package com.blamejared.contenttweaker.blocks.types.machine.gui.capability;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.capability.builder.*;
import com.blamejared.crafttweaker.impl.util.*;

import javax.annotation.*;
import java.util.*;

public class GuiCapabilityConfiguration implements ICoTCapabilityConfiguration {
    
    private final GuiGuiInformation guiInformation;
    
    public GuiCapabilityConfiguration(GuiGuiInformation guiInformation) {
        this.guiInformation = guiInformation;
    }
    
    @Override
    @Nullable
    public ICotCapabilityInstance createCapabilityInstance(CoTCapabilityInstanceManager instanceManager) {
        return null;
    }
    
    @Override
    public void addToScreen(CoTScreen screen) {
        guiInformation.addToScreen(screen);
    }
    
    @Override
    public Collection<WriteableResource> getResourcePackResources(MCResourceLocation blockName) {
        return guiInformation.getResourcePackResources(blockName);
    }
    
}

package com.blamejared.contenttweaker.blocks.types.machine.gui.capability.builder;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.capability.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.GuiCapabilityBuilder")
public class GuiCapabilityBuilder implements IIsCoTCapabilityBuilder {
    
    private final GuiGuiInformation guiInformation;
    
    public GuiCapabilityBuilder() {
        guiInformation = new GuiGuiInformation();
    }
    
    @ZenCodeType.Method
    public GuiCapabilityBuilder withHeight(int height) {
        this.guiInformation.setHeight(height);
        return this;
    }
    
    @ZenCodeType.Method
    public GuiCapabilityBuilder withWidth(int width) {
        this.guiInformation.setWidth(width);
        return this;
    }
    
    @ZenCodeType.Method
    public GuiCapabilityBuilder withBackground(String name){
        final MCResourceLocation mcResourceLocation = new MCResourceLocation(ContentTweaker.MOD_ID, name);
        this.guiInformation.setBackgroundLocation(mcResourceLocation);
        return this;
    }
    
    @Override
    public void applyToBuilder(BuilderMachine builderMachine) {
        final GuiCapabilityConfiguration configuration = new GuiCapabilityConfiguration(guiInformation);
        builderMachine.addCapability(CoTCapabilities.GUI, configuration);
    }
}

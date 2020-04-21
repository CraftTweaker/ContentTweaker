package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.VanillaFactory")
public class VanillaFactory {
    
    public static boolean registerLocked = false;
    
    public static void registerItem(MCItemRepresentation representation, String name) {
        registerItem(representation, new MCResourceLocation(new ResourceLocation(ContentTweaker.MODID, name)));
    }
    
    @ZenCodeType.Method
    public static void registerItem(MCItemRepresentation representation, MCResourceLocation resourceLocation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                ForgeRegistries.ITEMS.register(new CoTItem(representation).setRegistryName(resourceLocation
                        .getInternal()));
            }
            
            @Override
            public String describe() {
                return String.format("Registering item %s with resource location %s", representation
                        .getDisplayName(), resourceLocation.getCommandString());
            }
            
            @Override
            public boolean validate(ILogger logger) {
                if(registerLocked) {
                    logger.error("Cannot register items after setupCommon!");
                    logger.error("Ignoring Registration for item " + representation.getDisplayName());
                    return false;
                }
                
                return true;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return true;
            }
        });
    }
}

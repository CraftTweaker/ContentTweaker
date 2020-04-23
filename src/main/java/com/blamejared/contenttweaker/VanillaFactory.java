package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

public class VanillaFactory {
    
    public static boolean registerLocked = false;
    public static IForgeRegistry<Item> registry = null;
    
    public static void registerItem(CoTItem item, MCResourceLocation resourceLocation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                ForgeRegistries.ITEMS.register(item.setRegistryName(resourceLocation.getInternal()));
            }
            
            @Override
            public String describe() {
                return String.format("Registering item %s with resource location %s", item, resourceLocation
                        .getCommandString());
            }
            
            @Override
            public boolean validate(ILogger logger) {
                if(registry == null) {
                    logger.error("Registering items too early");
                    logger.error("Ignoring Registration for item " + item);
                    return false;
                }
                
                if(registerLocked) {
                    logger.error("Cannot register items after setupCommon!");
                    logger.error("Ignoring Registration for item " + item);
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

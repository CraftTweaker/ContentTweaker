package com.teamacronymcoders.tailoredobjects;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = TailoredObjects.MOD_ID,
        name = TailoredObjects.MOD_NAME,
        version = TailoredObjects.VERSION
)
public class TailoredObjects {

    public static final String MOD_ID = "tailoredobjects";
    public static final String MOD_NAME = "TailoredObjects";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}

package com.teamacronymcoders.tailoredobjects;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.tailoredobjects.api.TailoredObjectsAPI;
import com.teamacronymcoders.tailoredobjects.api.deserializer.RegisterDeserializerEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.teamacronymcoders.tailoredobjects.TailoredObjects.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class TailoredObjects extends BaseModFoundation<TailoredObjects> {
    public static final String MOD_ID = "tailoredobjects";
    public static final String MOD_NAME = "TailoredObjects";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDS = "required-after:base@[0.0.0,);";

    @Instance(MOD_ID)
    public static TailoredObjects instance;

    public TailoredObjects() {
        super(MOD_ID, MOD_NAME, VERSION, CreativeTabs.MISC);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.post(new RegisterDeserializerEvent(TailoredObjectsAPI.getInstance().getDeserializerRegistry()));
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public TailoredObjects getInstance() {
        return this;
    }
}

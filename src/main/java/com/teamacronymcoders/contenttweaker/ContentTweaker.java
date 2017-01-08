package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class ContentTweaker extends BaseModFoundation<ContentTweaker> {
    public static final String MOD_ID = "contenttweaker";
    public static final String MOD_NAME = "ContentTweaker";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDS = "required-after:base@[0.0.0,);";

    @Instance(MOD_ID)
    public static ContentTweaker instance;

    public ContentTweaker() {
        super(MOD_ID, MOD_NAME, VERSION, CreativeTabs.MISC);
        ContentTweakerAPI.setInstance(new ContentTweakerAPI(new ModWrapper()));
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.post(new RegisterDeserializerEvent(ContentTweakerAPI.getInstance().getDeserializerRegistry()));
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
    public ContentTweaker getInstance() {
        return this;
    }
}

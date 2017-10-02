package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.runtime.CrTTweaker;
import crafttweaker.runtime.ITweaker;
import crafttweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class ContentTweaker extends BaseModFoundation<ContentTweaker> {
    public static final String MOD_ID = "contenttweaker";
    public static final String MOD_NAME = "ContentTweaker";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDS = "required-after:base@[0.0.0,);required-after:crafttweaker;";

    @Instance(MOD_ID)
    public static ContentTweaker instance;

    public static boolean scriptsSuccessful;

    public ContentTweaker() {
        super(MOD_ID, MOD_NAME, VERSION, null, false);
        ContentTweakerAPI.setInstance(new ContentTweakerAPI(new ModWrapper()));
        this.subBlockSystem = new SubBlockSystem(this);
        this.materialUser = new CTMaterialUser(this);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
        scriptsSuccessful = CraftTweakerAPI.tweaker.loadScript(false, "contenttweaker");
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
    public boolean addOBJDomain() {
        return true;
    }

    @Override
    public boolean hasExternalResources() {
        return true;
    }

    @Override
    public ContentTweaker getInstance() {
        return this;
    }
}

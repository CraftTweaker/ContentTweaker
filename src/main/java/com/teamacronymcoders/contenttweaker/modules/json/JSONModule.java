package com.teamacronymcoders.contenttweaker.modules.json;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@Module(MOD_ID)
public class JSONModule extends ModuleBase {
    public File jsonFolder;

    @Override
    public String getName() {
        return "JSON Support";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        jsonFolder = new File(ContentTweaker.instance.contentFolder, "json");
        BaseFileUtils.createFolder(jsonFolder);
    }

    @Override
    public void afterModulesPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.post(new RegisterDeserializerEvent(ContentTweakerAPI.getInstance().getDeserializerRegistry()));
        Deserializer deserializer = new Deserializer(jsonFolder);
        deserializer.deserialize();
    }
}

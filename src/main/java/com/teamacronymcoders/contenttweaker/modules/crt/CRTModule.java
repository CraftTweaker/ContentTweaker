package com.teamacronymcoders.contenttweaker.modules.crt;

import com.teamacronymcoders.base.modulesystem.*;
import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import minetweaker.*;
import minetweaker.mc1102.brackets.ItemBracketHandler;
import net.minecraftforge.fml.common.event.*;

import java.io.File;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@Module(MOD_ID)
public class CRTModule extends ModuleBase {
    public File scriptFolder;

    @Override
    public String getName() {
        return "CraftTweaker Support";
    }

    @Override
    public void afterModulesPreInit(FMLPreInitializationEvent event) {
        scriptFolder = new File(ContentTweaker.instance.contentFolder, "scripts");
        BaseFileUtils.createFolder(scriptFolder);
        CraftTweakerRegistration.init(scriptFolder);
    }
}

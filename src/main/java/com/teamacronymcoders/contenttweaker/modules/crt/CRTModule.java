package com.teamacronymcoders.contenttweaker.modules.crt;

import com.teamacronymcoders.base.modulesystem.*;
import com.teamacronymcoders.base.util.files.BaseFileUtils;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.contenttweaker.modules.crt.classes.MCCreativeTab;
import com.teamacronymcoders.contenttweaker.modules.json.Deserializer;
import minetweaker.*;
import minetweaker.mc1102.brackets.ItemBracketHandler;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraftforge.common.MinecraftForge;
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
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		scriptFolder = new File(ContentTweaker.instance.contentFolder, "scripts");
		BaseFileUtils.createFolder(scriptFolder);
		MCCreativeTab.init();
		MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
		MineTweakerAPI.registerClass(MCCreativeTab.class);
	}
	
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
//		MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderDirectory(scriptFolder));
//		MineTweakerImplementationAPI.reload();
	}
	
	@Override
	public void afterModulesPreInit(FMLPreInitializationEvent event) {
//		MinecraftForge.EVENT_BUS.post(new RegisterDeserializerEvent(ContentTweakerAPI.getInstance().getDeserializerRegistry()));
//		Deserializer deserializer = new Deserializer(jsonFolder);
//		deserializer.deserialize();
//		MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
//		MineTweakerAPI.registerClass(MCCreativeTab.class);
//		MCCreativeTab.init();
		
	}
}

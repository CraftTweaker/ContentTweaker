package com.teamacronymcoders.contenttweaker.modules.crt;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.mc1102.brackets.ItemBracketHandler;
import minetweaker.runtime.providers.ScriptProviderDirectory;

import java.io.File;

public class CraftTweakerRegistration {
    public static void init(File scriptsDirectory) {

        boolean fileExists = scriptsDirectory.exists();
        if (!fileExists) {
            fileExists = scriptsDirectory.mkdir();
        }

        if (fileExists) {
            MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
            ItemBracketHandler.rebuildItemRegistry();

            MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderDirectory(scriptsDirectory));
            MineTweakerImplementationAPI.reload();
        } else {
            ContentTweaker.instance.getLogger().fatal("SCRIPTS DIRECTORY HAS FAILED TO BE CREATED");
        }
    }
}

package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.resource.RuntimeResourceDumpingCommand;
import com.blamejared.contenttweaker.core.zen.bracket.ContentTweakerBrackets;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import net.minecraft.network.chat.TranslatableComponent;

@CraftTweakerPlugin(ContentTweakerConstants.MOD_ID + ":core")
@SuppressWarnings("unused")
public final class ContentTweakerPlugin implements ICraftTweakerPlugin {

    public ContentTweakerPlugin() {
        ContentTweakerCore.LOGGER.info("CrT plugin initialized: bootstrapping CoT plugins");
        ContentTweakerCore.core().initializePlugins();
    }

    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        handler.registerLoader(ContentTweakerConstants.CONTENT_LOADER_ID);
    }

    @Override
    public void registerLoadSource(final IScriptLoadSourceRegistrationHandler handler) {
        handler.registerLoadSource(ContentTweakerConstants.PRE_REGISTRY_LOAD_SOURCE);
    }

    @Override
    public void registerModuleConfigurators(final IScriptRunModuleConfiguratorRegistrationHandler handler) {
        handler.registerConfigurator(ContentTweakerConstants.CONTENT_LOADER_ID, IScriptRunModuleConfigurator.createDefault(ContentTweakerZenConstants.MAIN_PACKAGE));
    }

    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        ContentTweakerBrackets.registerBrackets(handler);
    }

    @Override
    public void registerListeners(final IListenerRegistrationHandler handler) {
        handler.onCraftTweakerLoadCompletion(ContentTweakerCore.core()::loadContentScripts);
    }

    @Override
    public void registerCommands(final ICommandRegistrationHandler handler) {
        RuntimeResourceDumpingCommand.register(handler);
    }
}

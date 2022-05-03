package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.plugin.PluginManager;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class ContentTweakerCore {

    private static final ContentTweakerCore INSTANCE = new ContentTweakerCore();

    public static final Logger LOGGER = LogUtils.getLogger();

    private final MetaRegistry metaRegistry;
    private final PluginManager pluginManager;

    private ContentTweakerCore() {
        this.metaRegistry = MetaRegistry.of();
        this.pluginManager = PluginManager.of();
    }

    public static ContentTweakerCore core() {
        return INSTANCE;
    }

    public void initialize() {

    }

    public void initializePlugins() {
        this.pluginManager.initializePlugins(this.metaRegistry);
    }

    public void registerPluginBrackets(final IBracketParserRegistrationHandler handler) {
        this.pluginManager.registerPluginBrackets(handler);
    }

    public void loadContentScripts() {
        final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                ContentTweakerConstants.CONTENT_LOADER_ID,
                ContentTweakerConstants.PRE_REGISTRY_LOAD_SOURCE,
                ScriptRunConfiguration.RunKind.EXECUTE
        );
        final IScriptRun run = CraftTweakerAPI.getScriptRunManager().createScriptRun(configuration);
        try {
            run.execute();
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error("An error occurred while trying to run ContentTweaker scripts", e);
        }
    }

    public MetaRegistry metaRegistry() {
        return this.metaRegistry;
    }
}

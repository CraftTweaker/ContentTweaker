package com.blamejared.contenttweaker.core.api.action;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import org.apache.logging.log4j.Logger;

public interface ContentTweakerAction extends IAction {
    @Override
    default String systemName() {
        return ContentTweakerConstants.MOD_NAME;
    }

    @Override
    default boolean shouldApplyOn(final IScriptLoadSource source, final Logger logger) {
        return this.assertLoader(IScriptLoader.find(ContentTweakerConstants.CONTENT_LOADER_ID), logger) &&
                IScriptLoadSource.find(ContentTweakerConstants.PRE_REGISTRY_LOAD_SOURCE).equals(source);
    }
}

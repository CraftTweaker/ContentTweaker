package com.blamejared.contenttweaker.core.api.action;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

public interface ContentTweakerAction extends IAction {
    @Override
    default boolean shouldApplyOn(final IScriptLoadSource source) {
        return this.assertLoader(IScriptLoader.find(ContentTweakerConstants.CONTENT_LOADER_ID)) &&
                IScriptLoadSource.find(ContentTweakerConstants.PRE_REGISTRY_LOAD_SOURCE).equals(source);
    }
}

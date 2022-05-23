package com.blamejared.contenttweaker.core.api.resource;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import net.minecraft.server.packs.PackType;

public interface ResourceManager {
    static ResourceManager get() {
        return ContentTweakerApi.get().resourceManager();
    }

    ResourceFragment fragment(final ResourceFragment.Key key);
    ResourceTemplateHelper templateHelper();

    default ResourceFragment fragment(final PackType type, final String id) {
        return this.fragment(new ResourceFragment.Key(type, id));
    }
}

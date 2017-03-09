package com.teamacronymcoders.contenttweaker.modules.vanilla.resource.material;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resource.ResourceBracketHandler;

public class MaterialBracketHandler extends ResourceBracketHandler {
    public MaterialBracketHandler() {
        super("Material", MaterialBracketHandler.class);
    }

    public IMaterialDefinition getMaterial(String name) {
        return new MaterialDefinition(ContentTweakerAPI.getInstance().getBlockMaterials().getResource(name));
    }
}

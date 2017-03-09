package com.teamacronymcoders.contenttweaker.modules.vanilla.resource.material;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resource.ResourceBracketHandler;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.material.Material;

public class MaterialBracketHandler extends ResourceBracketHandler {
    public MaterialBracketHandler() {
        super("Material", MaterialBracketHandler.class);
    }

    public static IMaterialDefinition getMaterial(String name) {
        Material material = ContentTweakerAPI.getInstance().getBlockMaterials().getResource(name);
        if(material == null) {
            MineTweakerAPI.logError("Could not find Material for name: " + name);
        }
        return new MaterialDefinition(material);
    }
}

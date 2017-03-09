package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
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

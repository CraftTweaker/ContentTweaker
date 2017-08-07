package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.material.Material;

public class MaterialBracketHandler extends ResourceBracketHandler {
    public MaterialBracketHandler() {
        super("BlockMaterial", MaterialBracketHandler.class);
    }

    public static IMaterialDefinition getBlockMaterial(String name) {
        Material material = ContentTweakerAPI.getInstance().getBlockMaterials().getResource(name);
        if(material == null) {
            CraftTweakerAPI.logError("Could not find BlockMaterial for name: " + name);
        }
        return new MaterialDefinition(material);
    }
}

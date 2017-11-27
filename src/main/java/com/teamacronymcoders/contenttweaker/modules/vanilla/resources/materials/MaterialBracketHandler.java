package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.BlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.material.Material;

public class MaterialBracketHandler extends ResourceBracketHandler {
    public MaterialBracketHandler() {
        super("BlockMaterial", MaterialBracketHandler.class);
    }

    public static IBlockMaterialDefinition getBlockMaterial(String name) {
        Material material = ContentTweakerAPI.getInstance().getBlockMaterials().getResource(name);
        BlockMaterialDefinition materialDefinition = null;

        if(material == null) {
            CraftTweakerAPI.logError("Could not find BlockMaterial for name: " + name);
        } else {
            materialDefinition = new BlockMaterialDefinition(material);
        }

        return materialDefinition;
    }
}

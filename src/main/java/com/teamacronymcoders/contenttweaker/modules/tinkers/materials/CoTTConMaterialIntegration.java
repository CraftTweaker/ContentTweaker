package com.teamacronymcoders.contenttweaker.modules.tinkers.materials;

import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class CoTTConMaterialIntegration extends MaterialIntegration {

    public CoTTConMaterialIntegration(Material material) {
        super(material, material.getFluid());
    }

    @Override
    public void preInit() {

    }

    @Override
    public void integrate() {
        if (material.isCastable()) {
            TinkerSmeltery.registerToolpartMeltingCasting(material);
        }
    }
}

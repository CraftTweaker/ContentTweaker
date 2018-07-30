package com.teamacronymcoders.contenttweaker.modules.tinkers;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.CoTTConMaterial;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.CoTTConMaterialBuilder;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.List;

@Module(ContentTweaker.MOD_ID)
public class TinkersModule extends ModuleBase {

    @Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
        dependencies.add(new ModDependency("tconstruct"));
        return super.getDependencies(dependencies);
    }

    @Override
    public String getName() {
        return "Tinkers";
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        CoTTConMaterialBuilder.addedMaterials.forEach(CoTTConMaterial::registerTraits);
    }
}

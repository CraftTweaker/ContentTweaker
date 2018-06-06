package com.teamacronymcoders.contenttweaker.modules.chickens;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.List;

@Module(ContentTweaker.MOD_ID)
public class ChickenModule extends ModuleBase {
    @Override
    public String getName() {
        return "Chickens";
    }

    @Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
        dependencies.add(new ModDependency("chickens"));
        return super.getDependencies(dependencies);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ChickenFactory.registerChickens();
    }
}

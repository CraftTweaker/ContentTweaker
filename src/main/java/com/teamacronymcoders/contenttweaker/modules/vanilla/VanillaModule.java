package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.IBlock;
import minetweaker.MineTweakerAPI;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@Module(MOD_ID)
public class VanillaModule extends ModuleBase {
    @Override
    public String getName() {
        return "Vanilla CraftTweaker";
    }

    @Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
        dependencies.add(new ModuleDependency("CraftTweaker Support"));
        return dependencies;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MineTweakerAPI.registerClass(IBlock.class);
        MineTweakerAPI.registerClass(VanillaFactory.class);
    }
}

package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.modules.materials.functions.IRegisterMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPartBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;
import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@Module(MOD_ID)
public class MaterialModule extends ModuleBase {
    @Override
    public String getName() {
        return "Materials";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        CraftTweakerAPI.registerClass(IPartType.class);
        CraftTweakerAPI.registerClass(IPartDataPiece.class);
        CraftTweakerAPI.registerClass(IMaterialPartData.class);
        CraftTweakerAPI.registerClass(IPart.class);
        CraftTweakerAPI.registerClass(IPartBuilder.class);
        CraftTweakerAPI.registerClass(IMaterial.class);
        CraftTweakerAPI.registerClass(IMaterialBuilder.class);
        CraftTweakerAPI.registerClass(IMaterialPart.class);
        CraftTweakerAPI.registerClass(IRegisterMaterialPart .class);
        CraftTweakerAPI.registerClass(CTMaterialSystem.class);

        CraftTweakerAPI.registerBracketHandler(new MaterialPartBracketHandler());
    }
}

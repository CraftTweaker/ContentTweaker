package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.api.utils.ResourceListCommand;
import com.teamacronymcoders.contenttweaker.modules.materials.brackethandler.MaterialPartBracketHandler;
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
import crafttweaker.mc1120.commands.CTChatCommand;
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

        CTChatCommand.registerCommand(new ResourceListCommand("materialparts", MaterialSystem.getMaterialParts().keySet()));
        CTChatCommand.registerCommand(new ResourceListCommand("materials", MaterialSystem.getMaterials().keySet()));
        CTChatCommand.registerCommand(new ResourceListCommand("parts", MaterialSystem.getParts().keySet()));
        CTChatCommand.registerCommand(new ResourceListCommand("parttypes", MaterialSystem.getPartTypes().keySet()));

        CraftTweakerAPI.registerBracketHandler(new MaterialPartBracketHandler());
    }
}

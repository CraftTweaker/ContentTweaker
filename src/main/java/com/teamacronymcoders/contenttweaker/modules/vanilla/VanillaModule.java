package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.ctobjects.biome.ICTBiome;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import com.teamacronymcoders.contenttweaker.api.utils.ResourceListCommand;
import com.teamacronymcoders.contenttweaker.modules.vanilla.fluids.FluidRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.BlockBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.CreativeTabBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.MaterialBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundEventBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundTypeBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.utils.commands.Commands;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@Module(MOD_ID)
public class VanillaModule extends ModuleBase {
    @Override
    public String getName() {
        return "Vanilla CraftTweaker";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        CraftTweakerAPI.registerBracketHandler(new MaterialBracketHandler());
        CTChatCommand.registerCommand(new ResourceListCommand<>("blockmaterial",
                ContentTweakerAPI.getInstance().getBlockMaterials()));
        CraftTweakerAPI.registerBracketHandler(new CreativeTabBracketHandler());
        CTChatCommand.registerCommand(new ResourceListCommand<>("creativetab",
                ContentTweakerAPI.getInstance().getCreativeTabs()));
        CraftTweakerAPI.registerBracketHandler(new SoundEventBracketHandler());
        CTChatCommand.registerCommand(new ResourceListCommand<>("soundevent",
                ContentTweakerAPI.getInstance().getSoundTypes()));
        CraftTweakerAPI.registerBracketHandler(new SoundTypeBracketHandler());
        CTChatCommand.registerCommand(new ResourceListCommand<>("soundtype",
                ContentTweakerAPI.getInstance().getSoundTypes()));
        CraftTweakerAPI.registerBracketHandler(new BlockBracketHandler());

        CraftTweakerAPI.registerClass(ICTBiome.class);
        CraftTweakerAPI.registerClass(IWorld.class);
        CraftTweakerAPI.registerClass(IBlockPos.class);
        CraftTweakerAPI.registerClass(ICTBlockState.class);

        CraftTweakerAPI.registerClass(IItemRightClick.class);
        CraftTweakerAPI.registerClass(IBlockAction.class);

        CraftTweakerAPI.registerClass(ICreativeTab.class);
        CraftTweakerAPI.registerClass(BlockRepresentation.class);
        CraftTweakerAPI.registerClass(ItemRepresentation.class);
        CraftTweakerAPI.registerClass(FluidRepresentation.class);
        CraftTweakerAPI.registerClass(VanillaFactory.class);
        CraftTweakerAPI.registerClass(Commands.class);
    }
}

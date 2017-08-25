package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.api.ctobjects.biome.ICTBiome;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.IBlock;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.IItem;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.BlockBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.CreativeTabBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.MaterialBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundEventBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundTypeBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.utils.commands.Commands;
import crafttweaker.CraftTweakerAPI;
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
        CraftTweakerAPI.registerBracketHandler(new CreativeTabBracketHandler());
        CraftTweakerAPI.registerBracketHandler(new SoundEventBracketHandler());
        CraftTweakerAPI.registerBracketHandler(new SoundTypeBracketHandler());
        CraftTweakerAPI.registerBracketHandler(new BlockBracketHandler());

        CraftTweakerAPI.registerClass(ICTBiome.class);
        CraftTweakerAPI.registerClass(IWorld.class);
        CraftTweakerAPI.registerClass(IBlockPos.class);
        CraftTweakerAPI.registerClass(ICTBlockState.class);

        CraftTweakerAPI.registerClass(IItemRightClick.class);
        CraftTweakerAPI.registerClass(IBlockAction.class);

        CraftTweakerAPI.registerClass(ICreativeTab.class);
        CraftTweakerAPI.registerClass(IBlock.class);
        CraftTweakerAPI.registerClass(IItem.class);
        CraftTweakerAPI.registerClass(VanillaFactory.class);
        CraftTweakerAPI.registerClass(Commands.class);
    }
}

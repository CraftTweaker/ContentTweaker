package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.api.wrappers.world.IWorld;
import com.teamacronymcoders.contenttweaker.modules.vanilla.VanillaFactory;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.IBlock;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.IItem;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.CreativeTabBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.MaterialBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundEventBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundTypeBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.utils.commands.Commands;
import minetweaker.MineTweakerAPI;
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
        
    }
}

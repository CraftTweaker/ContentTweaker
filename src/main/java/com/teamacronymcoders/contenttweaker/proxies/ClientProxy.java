package com.teamacronymcoders.contenttweaker.proxies;

import com.teamacronymcoders.contenttweaker.renderers.ErrorSilencingLoader;
import com.teamacronymcoders.contenttweaker.resources.ResourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void createErrorSilencingLoader() {
        new ErrorSilencingLoader();
    }

    @Override
    public void createResourcePack() {
        ResourceLoader.assembleResourcePack();
    }
}

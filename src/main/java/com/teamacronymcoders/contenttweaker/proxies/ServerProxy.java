package com.teamacronymcoders.contenttweaker.proxies;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
    @Override
    public void createErrorSilencingLoader() {
        //No-op on Server
    }

    @Override
    public void createResourcePack() {
        //No-op on Server
    }
}

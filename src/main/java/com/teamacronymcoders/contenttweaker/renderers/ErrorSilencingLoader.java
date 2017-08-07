package com.teamacronymcoders.contenttweaker.renderers;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.MOD_ID;

@SideOnly(Side.CLIENT)
public class ErrorSilencingLoader implements ICustomModelLoader {
    private boolean firstReload = true;
    private boolean createdResourcePack = false;

    public ErrorSilencingLoader() {
        ModelLoaderRegistry.registerLoader(this);
    }

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return !createdResourcePack && modelLocation.getResourceDomain().equals(MOD_ID);
    }

    @Override
    @Nonnull
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws Exception {
        return new EmptyModel();
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        if (!firstReload) {
            createdResourcePack = true;
        } else {
            firstReload = false;
        }
    }
}

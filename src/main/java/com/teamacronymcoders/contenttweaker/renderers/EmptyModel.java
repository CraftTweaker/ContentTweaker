package com.teamacronymcoders.contenttweaker.renderers;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.ArrayList;
import java.util.Collection;

public class EmptyModel implements IModel {
    private Collection<ResourceLocation> collection = new ArrayList<>();

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.copyOf(collection);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.copyOf(collection);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new EmptyBakedModel();
    }

    @Override
    public IModelState getDefaultState() {
        return new EmptyModelState();
    }
}

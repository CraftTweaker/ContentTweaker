package com.blamejared.contenttweaker.vanilla.api.zen.builder.material;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_BUILDER_PACKAGE + ".material.MaterialBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialBuilder {
    private final Reference<MaterialColor> color;

    private PushReaction pushReaction;
    private boolean blocksMotion;
    private boolean flammable;
    private boolean liquid;
    private boolean replaceable;
    private boolean solid;
    private boolean solidBlocking;

    private MaterialBuilder(final Reference<MaterialColor> reference) {
        this.color = reference;
        this.pushReaction = null;
        this.blocksMotion = false;
        this.flammable = false;
        this.liquid = false;
        this.replaceable = false;
        this.solid = false;
        this.solidBlocking = false;
    }

    public static MaterialBuilder of(final Reference<MaterialColor> reference) {
        return new MaterialBuilder(Objects.requireNonNull(reference));
    }

    @ZenCodeType.Method("pushReaction")
    public MaterialBuilder pushReaction(final PushReaction reaction) {
        this.pushReaction = Objects.requireNonNull(reaction);
        return this;
    }

    @ZenCodeType.Method("blocksMotion")
    public MaterialBuilder blocksMotion() {
        this.blocksMotion = true;
        return this;
    }

    @ZenCodeType.Method("flammable")
    public MaterialBuilder flammable() {
        this.flammable = true;
        return this;
    }

    @ZenCodeType.Method("liquid")
    public MaterialBuilder liquid() {
        this.liquid = true;
        return this;
    }

    @ZenCodeType.Method("replaceable")
    public MaterialBuilder replaceable() {
        this.replaceable = true;
        return this;
    }

    @ZenCodeType.Method("solid")
    public MaterialBuilder solid() {
        this.solid = true;
        return this;
    }

    @ZenCodeType.Method("solidBlocking")
    public MaterialBuilder solidBlocking() {
        this.solidBlocking = true;
        return this;
    }

    @ZenCodeType.Method("build")
    public SimpleReference<Material> build(final String name) {
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final ObjectHolder<Material> holder = ObjectHolder.of(VanillaObjectTypes.MATERIAL, id, this::make);
        ContentTweakerApi.apply(RegisterObjectAction.of(holder));
        return SimpleReference.of(VanillaObjectTypes.MATERIAL, id);
    }

    private Material make() {
        return new Material(
                this.color.get(),
                this.liquid,
                this.solid,
                this.blocksMotion,
                this.solidBlocking,
                this.flammable,
                this.replaceable,
                Optional.ofNullable(this.pushReaction).orElse(PushReaction.NORMAL)
        );
    }
}

package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.action.material.CreateMaterialAction;
import com.blamejared.contenttweaker.vanilla.api.util.MaterialRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".MaterialBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialBuilder {
    private final MaterialColorReference color;

    private PushReaction pushReaction;
    private boolean blocksMotion;
    private boolean flammable;
    private boolean liquid;
    private boolean replaceable;
    private boolean solid;
    private boolean solidBlocking;

    private MaterialBuilder(final MaterialColorReference reference) {
        this.color = reference;
        this.pushReaction = null;
        this.blocksMotion = false;
        this.flammable = false;
        this.liquid = false;
        this.replaceable = false;
        this.solid = false;
        this.solidBlocking = false;
    }

    public static MaterialBuilder of(final MaterialColorReference reference) {
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
    public MaterialReference build(final String name) {
        final ResourceLocation id = ContentTweakerConstants.rl(
                NameUtil.fixing(
                        name,
                        (fix, mistakes) -> CraftTweakerAPI.LOGGER.warn(
                                "The given name '{}' is not valid: it has been fixed to '{}'.\nMistakes:\n{}",
                                name,
                                fix,
                                String.join("\n", mistakes)
                        )
                )
        );
        return MaterialReference.of(id, () -> {
            final Material[] material = new Material[1];
            ContentTweakerApi.apply(CreateMaterialAction.of(id, () -> material[0] = MaterialRegistry.of().register(id, this::make)));
            return material[0];
        });
    }

    private Material make() {
        return new Material(
                this.color.unwrap(),
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

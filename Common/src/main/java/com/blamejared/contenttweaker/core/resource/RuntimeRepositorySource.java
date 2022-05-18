package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class RuntimeRepositorySource implements RepositorySource {
    private static final int CLIENT_FORMAT = 8;
    private static final int SERVER_FORMAT = 8;

    private final PackType type;

    private RuntimeRepositorySource(final PackType type) {
        this.type = type;
    }

    public static RuntimeRepositorySource of(final PackType type) {
        Objects.requireNonNull(type);
        return new RuntimeRepositorySource(type);
    }

    @Override
    public void loadPacks(final Consumer<Pack> consumer, final Pack.PackConstructor packConstructor) {
        final RuntimeResourceManager manager = ContentTweakerCore.core().resourceManager();
        final Map<String, RuntimeFragment> resources = manager.fragments(this.type);
        resources.forEach((namespace, fragment) -> consumer.accept(this.pack(this.type, namespace, fragment, packConstructor)));
    }

    private Pack pack(final PackType type, final String id, final RuntimeFragment fragment, final Pack.PackConstructor constructor) {
        final String packId = ContentTweakerConstants.rl("runtime/" + fragment.fsId().replace(':', '/')).toString();
        final Pack pack = Pack.create(packId, true, () -> this.createPack(type, id, packId, fragment), constructor, Pack.Position.TOP, this::decorateSource);
        if (pack == null) {
            throw new IllegalStateException("An error occurred while generating runtime " + ContentTweakerConstants.MOD_NAME + " pack '" + packId + "'");
        }
        return pack;
    }

    private PackResources createPack(final PackType type, final String target, final String packId, final RuntimeFragment fragment) {
        return new RuntimePack(packId, target, type, this.makeMetadata(type, packId, target), fragment::fs);
    }

    private JsonObject makeMetadata(final PackType type, final String packId, final String target) {
        final JsonObject meta = new JsonObject();

        final JsonObject pack = new JsonObject();
        pack.addProperty("description", "Runtime pack for " + target + " of type " + type.name());
        pack.addProperty("pack_format", this.makeFormat(type));
        meta.add("pack", pack);

        final JsonObject cotData = new JsonObject();
        cotData.addProperty("type", type.toString());
        cotData.addProperty("id", packId);
        cotData.addProperty("target", target);
        meta.add(ContentTweakerConstants.rl("data").toString(), cotData);

        return meta;
    }

    private int makeFormat(final PackType type) {
        return type.getVersion(SharedConstants.getCurrentVersion());
    }

    private Component decorateSource(final Component originalName) {
        return new TranslatableComponent(
                "pack.nameAndSource",
                originalName,
                new TranslatableComponent(ContentTweakerConstants.ln("pack_source.runtime"))
        );
    }
}

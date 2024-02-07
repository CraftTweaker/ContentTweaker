package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RuntimeRepositorySource implements RepositorySource {
    private final PackType type;

    private RuntimeRepositorySource(final PackType type) {
        this.type = type;
    }

    public static RuntimeRepositorySource of(final PackType type) {
        Objects.requireNonNull(type);
        return new RuntimeRepositorySource(type);
    }

    @Override
    public void loadPacks(final Consumer<Pack> consumer) {
        final RuntimeResourceManager manager = ContentTweakerCore.core().resourceManager();
        final Map<String, RuntimeFragment> resources = manager.fragments(this.type);
        resources.forEach((namespace, fragment) -> consumer.accept(this.pack(this.type, namespace, fragment)));
    }

    private Pack pack(final PackType type, final String id, final RuntimeFragment fragment) {
        final String packId = ContentTweakerConstants.rl("runtime/" + fragment.fsId().replace(':', '/')).toString();
        final Component packTitle = Component.translatable(ContentTweakerConstants.ln("pack.title.runtime"), fragment.fsId());
        final Pack.ResourcesSupplier resources = this.createPack(type, id, packId, fragment);
        final PackSource source = PackSource.create(this::decorateSource, true);
        final Pack pack = Pack.readMetaAndCreate(packId, packTitle, true, resources, type, Pack.Position.TOP, source);
        if (pack == null) {
            throw new IllegalStateException("An error occurred while generating runtime " + ContentTweakerConstants.MOD_NAME + " pack '" + packId + "'");
        }
        return pack;
    }

    private Pack.ResourcesSupplier createPack(final PackType type, final String target, final String packId, final RuntimeFragment fragment) {
        final RuntimePack pack = new RuntimePack(packId, target, type, this.makeMetadata(type, packId, target), fragment::fs);
        return name -> new RuntimePackResources(pack);
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
        return SharedConstants.getCurrentVersion().getPackVersion(type);
    }

    private Component decorateSource(final Component originalName) {
        return Component.translatable(
                "pack.nameAndSource",
                originalName,
                Component.translatable(ContentTweakerConstants.ln("pack.source.runtime"))
        ).withStyle(ChatFormatting.GRAY);
    }
}

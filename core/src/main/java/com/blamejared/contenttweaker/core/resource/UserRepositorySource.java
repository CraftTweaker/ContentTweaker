package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.ContentTweakerLoggers;
import com.blamejared.contenttweaker.core.service.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public final class UserRepositorySource implements RepositorySource {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setLenient().create();
    private static final Path TARGET = ServiceManager.platform().gameDirectory().resolve("resources"); // TODO("Maybe move somewhere else?")

    private final PackType type;

    private UserRepositorySource(final PackType type) {
        this.type = type;
    }

    public static UserRepositorySource of(final PackType type) {
        return new UserRepositorySource(Objects.requireNonNull(type));
    }

    @Override
    public void loadPacks(final Consumer<Pack> consumer) {
        if (this.verify()) {
            return;
        }
        try {
            this.checkUserResources(consumer);
        } catch (final IOException e) {
            ContentTweakerLoggers.resources().warn("An error occurred while walking user resources", e);
        }
    }

    private boolean verify() {
        if (!Files.isDirectory(TARGET)) {
            try {
                Files.deleteIfExists(TARGET);
                Files.createDirectory(TARGET);
            } catch (final IOException e) {
                ContentTweakerLoggers.resources().error("An error occurred while trying to create user directory in " + TARGET + ": user resources won't be available", e);
                return true;
            }
        }
        return false;
    }

    private void checkUserResources(final Consumer<Pack> consumer) throws IOException {
        consumer.accept(this.pack());
    }

    private Pack pack() throws IOException {
        final Path metadataPath = TARGET.resolve("pack.mcmeta");
        final JsonObject metadata = this.readMetadata(metadataPath);
        final String packId = ContentTweakerConstants.rl("user/" + this.type.name().toLowerCase(Locale.ENGLISH)).toString();
        final Component packTitle = Component.translatable(ContentTweakerConstants.ln("pack.title.user"));
        final PackSource source = PackSource.create(this::decorateSource, true);
        final Pack pack = Pack.readMetaAndCreate(packId, packTitle, true, name -> this.resources(name, metadata), type, Pack.Position.TOP, source);
        if (pack == null) {
            throw new IOException("Unable to create pack due to an unknown IO error", new NullPointerException());
        }
        return pack;
    }

    private JsonObject readMetadata(final Path path) throws IOException {
        if (Files.exists(path)) {
            try (final InputStreamReader reader = new InputStreamReader(Files.newInputStream(path, StandardOpenOption.READ))) {
                return GSON.fromJson(reader, JsonObject.class);
            }
        }
        return this.createMetadata();
    }

    private JsonObject createMetadata() {
        final JsonObject meta = new JsonObject();

        final JsonObject pack = new JsonObject();
        pack.addProperty("description", ContentTweakerConstants.MOD_NAME + " User Resources");
        pack.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(this.type));
        meta.add("pack", pack);

        return meta;
    }

    private PackResources resources(final String name, final JsonObject metadata) {
        final PackResources resources = new PathPackResources(name, TARGET, true);
        return new UserPack(GSON, name, resources, metadata);
    }

    private Component decorateSource(final Component originalName) {
        return Component.translatable(
                "pack.nameAndSource",
                originalName,
                Component.translatable(ContentTweakerConstants.ln("pack.source.user"))
        ).withStyle(ChatFormatting.GRAY);
    }
}

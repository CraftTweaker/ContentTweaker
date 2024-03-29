package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class PathHelper {
    private static final Supplier<Map<ObjectType<?>, String>> SPECIAL_TAGS = Suppliers.memoize(() -> Map.of(
            VanillaObjectTypes.BLOCK, "blocks",
            // entity_type -> "entity_types",
            // fluid -> "fluids",
            // game_event -> "game_events",
            VanillaObjectTypes.ITEM, "items"
    ));

    private PathHelper() {}

    public static String blockLootTable(final ResourceLocation name) {
        return lootTable(new ResourceLocation(Objects.requireNonNull(name).getNamespace(), "blocks/%s".formatted(name.getPath())));
    }

    public static String blockModel(final ResourceLocation name) {
        return "models/block/%s.json".formatted(Objects.requireNonNull(name).getPath());
    }

    public static String blockState(final ResourceLocation name) {
        return "blockstates/%s.json".formatted(Objects.requireNonNull(name).getPath());
    }

    public static String itemModel(final ResourceLocation name) {
        return "models/item/%s.json".formatted(Objects.requireNonNull(name).getPath());
    }

    public static String lang(final String language) {
        return "lang/%s.json".formatted(Objects.requireNonNull(language));
    }

    public static String lootTable(final ResourceLocation table) {
        return "loot_tables/%s.json".formatted(Objects.requireNonNull(table).getPath());
    }

    public static String sound(final ResourceLocation id) {
        return "sounds/%s.ogg".formatted(Objects.requireNonNull(id).getPath());
    }

    public static String texture(final ResourceLocation name) {
        return "textures/%s.png".formatted(Objects.requireNonNull(name).getPath());
    }

    public static String tag(final ObjectType<?> type, final ResourceLocation name) {
        final String path = SPECIAL_TAGS.get().containsKey(Objects.requireNonNull(type))? SPECIAL_TAGS.get().get(type) : Objects.requireNonNull(type.key()).location().getPath();
        return "tags/%s/%s.json".formatted(path, Objects.requireNonNull(name).getPath());
    }

    public static String usLang() {
        return lang("en_us");
    }
}

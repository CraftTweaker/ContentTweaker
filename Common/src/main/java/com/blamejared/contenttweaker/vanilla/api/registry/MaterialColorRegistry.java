package com.blamejared.contenttweaker.vanilla.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MaterialColorRegistry implements GameRegistry<MaterialColor> {
    private final Object2IntMap<String> colors;
    private final Int2ObjectMap<String> inverseLookup;

    private MaterialColorRegistry(final Supplier<Object2IntMap<String>> gatherer, final Function<Object2IntMap<String>, Int2ObjectMap<String>> inverter) {
        this.colors = Objects.requireNonNull(gatherer).get();
        this.inverseLookup = Objects.requireNonNull(inverter).apply(this.colors);
    }

    public static MaterialColorRegistry of() {
        return new MaterialColorRegistry(MaterialColorRegistry::gather, MaterialColorRegistry::invert);
    }

    private static Object2IntMap<String> gather() {
        final Object2IntMap<String> map = new Object2IntArrayMap<>();
        map.defaultReturnValue(0);
        entry(map, "none", 0, 0);
        entry(map, "grass", 1, 0x7FB238);
        entry(map, "sand", 2, 0xF7E9A3);
        entry(map, "wool", 3, 0xC7C7C7);
        entry(map, "fire", 4, 0xFF0000);
        entry(map, "ice", 5, 0xA0A0FF);
        entry(map, "metal", 6, 0xA7A7A7);
        entry(map, "plant", 7, 0x007C00);
        entry(map, "snow", 8, 0xFFFFFF);
        entry(map, "clay", 9, 0xA4A8B8);
        entry(map, "dirt", 10, 0x976D4D);
        entry(map, "stone", 11, 0x707070);
        entry(map, "water", 12, 0x4040FF);
        entry(map, "wood", 13, 0x8F7748);
        entry(map, "quartz", 14, 0xFFFCF5);
        entry(map, "color_orange", 15, 0xD87F33);
        entry(map, "color_magenta", 16, 0xB24CD8);
        entry(map, "color_light_blue", 17, 0x6699D8);
        entry(map, "color_yellow", 18, 0xE5E533);
        entry(map, "color_light_green", 19, 0x7FCC19);
        entry(map, "color_pink", 20, 0xF27FA5);
        entry(map, "color_gray", 21, 0x4C4C4C);
        entry(map, "color_light_gray", 22, 0x999999);
        entry(map, "color_cyan", 23, 0x4C7F99);
        entry(map, "color_purple", 24, 0x7F3FB2);
        entry(map, "color_blue", 25, 0x334CB2);
        entry(map, "color_brown", 26, 0x664C33);
        entry(map, "color_green", 27, 0x667F33);
        entry(map, "color_red", 28, 0x993333);
        entry(map, "color_black", 29, 0x191919);
        entry(map, "gold", 30, 0xFAEE4D);
        entry(map, "diamond", 31, 0x5CDBD5);
        entry(map, "lapis", 32, 0x4A80FF);
        entry(map, "emerald", 33, 0x00D93A);
        entry(map, "podzol", 34, 0x815631);
        entry(map, "nether", 35, 0x700200);
        entry(map, "terracotta_white", 36, 0xD1B1A1);
        entry(map, "terracotta_orange", 37, 0x9F5224);
        entry(map, "terracotta_magenta", 38, 0x95576C);
        entry(map, "terracotta_light_blue", 39, 0x706C8A);
        entry(map, "terracotta_yellow", 40, 0xBA8524);
        entry(map, "terracotta_light_green", 41, 0x677535);
        entry(map, "terracotta_pink", 42, 0xA04D4E);
        entry(map, "terracotta_gray", 43, 0x392923);
        entry(map, "terracotta_light_gray", 44, 0x876B62);
        entry(map, "terracotta_cyan", 45, 0x575C5C);
        entry(map, "terracotta_purple", 46, 0x7A4958);
        entry(map, "terracotta_blue", 47, 0x4C3E5C);
        entry(map, "terracotta_brown", 48, 0x4C3223);
        entry(map, "terracotta_green", 49, 0x4C522A);
        entry(map, "terracotta_red", 50, 0x8E3C2E);
        entry(map, "terracotta_black", 51, 0x251610);
        entry(map, "crimson_nylium", 52, 0xBD3031);
        entry(map, "crimson_stem", 53, 0x943F61);
        entry(map, "crimson_hyphae", 54, 0x5C191D);
        entry(map, "warped_nylium", 55, 0x167E86);
        entry(map, "warped_stem", 56, 0x3A8E8C);
        entry(map, "warped_hyphae", 57, 0x562C3E);
        entry(map, "warped_wart_block", 58, 0x14B485);
        entry(map, "deepslate", 59, 0x646464);
        entry(map, "raw_iron", 60, 0xD8AF93);
        entry(map, "glow_lichen", 61, 0x7FA796);
        return map;
    }

    private static void entry(final Object2IntMap<String> map, final String name, final int id, final int color) {
        map.put(name.toLowerCase(Locale.ENGLISH), (id << 24) | (color & 0xFFFFFF));
    }

    private static Int2ObjectMap<String> invert(final Object2IntMap<String> map) {
        final Int2ObjectMap<String> inverse = new Int2ObjectArrayMap<>();
        map.object2IntEntrySet().forEach(it -> inverse.put(id(it.getIntValue()), it.getKey()));
        return inverse;
    }

    private static int id(final int packed) {
        return (packed >>> 24) & 0xFF;
    }

    private static int color(final int packed) {
        return packed & 0xFFFFFF;
    }

    @Override
    public ObjectType<MaterialColor> type() {
        return VanillaObjectTypes.MATERIAL_COLOR;
    }

    @Override
    public MaterialColor get(final ResourceLocation name) {
        return MaterialColor.byId(id(this.colors.getInt(Objects.requireNonNull(name).getPath())));
    }

    @Override
    public ResourceLocation nameOf(final MaterialColor object) {
        return new ResourceLocation(this.inverseLookup.get(Objects.requireNonNull(object).id));
    }

    @Override
    public Collection<MaterialColor> all() {
        return this.colors.values()
                .intStream()
                .map(MaterialColorRegistry::id)
                .mapToObj(MaterialColor::byId)
                .toList();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<MaterialColor> objectCreator) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(objectCreator);
        throw new IllegalStateException("Unable to register new material colors");
    }
}

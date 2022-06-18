package com.blamejared.contenttweaker.vanilla.api.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Material;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class MaterialRegistry {
    private static final Supplier<MaterialRegistry> INSTANCE = Suppliers.memoize(
            () -> new MaterialRegistry(MaterialRegistry::computeVanillaMaterials)
    );

    private final BiMap<ResourceLocation, Material> materials;
    private final BiMap<Material, ResourceLocation> inverse;

    private MaterialRegistry(final Supplier<Map<ResourceLocation, Material>> vanillaMaterialsGetter) {
        this.materials = HashBiMap.create(vanillaMaterialsGetter.get());
        this.inverse = this.materials.inverse();
    }

    public static MaterialRegistry of() {
        return INSTANCE.get();
    }

    private static Map<ResourceLocation, Material> computeVanillaMaterials() {
        final Map<ResourceLocation, Material> map = new HashMap<>();
        entry(map, "air", Material.AIR);
        entry(map, "structural_air", Material.STRUCTURAL_AIR);
        entry(map, "portal", Material.PORTAL);
        entry(map, "cloth_decoration", Material.CLOTH_DECORATION);
        entry(map, "plant", Material.PLANT);
        entry(map, "water_plant", Material.WATER_PLANT);
        entry(map, "replaceable_plant", Material.REPLACEABLE_PLANT);
        entry(map, "replaceable_fireproof_plant", Material.REPLACEABLE_FIREPROOF_PLANT);
        entry(map, "replaceable_water_plant", Material.REPLACEABLE_WATER_PLANT);
        entry(map, "water", Material.WATER);
        entry(map, "bubble_column", Material.BUBBLE_COLUMN);
        entry(map, "lava", Material.LAVA);
        entry(map, "top_snow", Material.TOP_SNOW);
        entry(map, "fire", Material.FIRE);
        entry(map, "decoration", Material.DECORATION);
        entry(map, "web", Material.WEB);
        entry(map, "sculk", Material.SCULK);
        entry(map, "buildable_glass", Material.BUILDABLE_GLASS);
        entry(map, "clay", Material.CLAY);
        entry(map, "dirt", Material.DIRT);
        entry(map, "grass", Material.GRASS);
        entry(map, "ice_solid", Material.ICE_SOLID);
        entry(map, "sand", Material.SAND);
        entry(map, "sponge", Material.SPONGE);
        entry(map, "shulker_shell", Material.SHULKER_SHELL);
        entry(map, "wood", Material.WOOD);
        entry(map, "nether_wood", Material.NETHER_WOOD);
        entry(map, "bamboo_sapling", Material.BAMBOO_SAPLING);
        entry(map, "bamboo", Material.BAMBOO);
        entry(map, "wool", Material.WOOL);
        entry(map, "explosive", Material.EXPLOSIVE);
        entry(map, "leaves", Material.LEAVES);
        entry(map, "glass", Material.GLASS);
        entry(map, "ice", Material.ICE);
        entry(map, "cactus", Material.CACTUS);
        entry(map, "stone", Material.STONE);
        entry(map, "metal", Material.METAL);
        entry(map, "snow", Material.SNOW);
        entry(map, "heavy_metal", Material.HEAVY_METAL);
        entry(map, "barrier", Material.BARRIER);
        entry(map, "piston", Material.PISTON);
        entry(map, "moss", Material.MOSS);
        entry(map, "vegetable", Material.VEGETABLE);
        entry(map, "egg", Material.EGG);
        entry(map, "cake", Material.CAKE);
        entry(map, "amethyst", Material.AMETHYST);
        entry(map, "powder_snow", Material.POWDER_SNOW);
        return map;
    }

    private static void entry(final Map<ResourceLocation, Material> map, final String name, final Material material) {
        map.put(new ResourceLocation(name.toLowerCase(Locale.ENGLISH)), material);
    }

    public Material register(final ResourceLocation name, final Supplier<Material> creator) {
        Objects.requireNonNull(creator);
        if (this.materials.containsKey(Objects.requireNonNull(name))) {
            throw new IllegalArgumentException("A tier with name " + name + " is already known");
        }
        final Material material = Objects.requireNonNull(creator.get());
        this.materials.put(name, material);
        return material;
    }

    public Material find(final ResourceLocation name) {
        return this.materials.computeIfAbsent(name, it -> {
            throw new IllegalArgumentException("No such material found for id " + it);
        });
    }

    public Stream<ResourceLocation> knownMaterials() {
        return this.materials.keySet().stream();
    }

    public ResourceLocation nameOf(final Material material) {
        return this.inverse.computeIfAbsent(material, it -> {
            throw new IllegalArgumentException("No such material " + it + " known: it is probably not registered");
        });
    }
}

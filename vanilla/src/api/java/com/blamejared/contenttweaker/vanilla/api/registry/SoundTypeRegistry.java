package com.blamejared.contenttweaker.vanilla.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SoundTypeRegistry implements GameRegistry<SoundType> {
    private final BiMap<ResourceLocation, SoundType> soundTypes;
    private final BiMap<SoundType, ResourceLocation> inverse;
    private final Consumer<BiMap<ResourceLocation, SoundType>> vanillaProvider;
    private final Map<ResourceLocation, Supplier<SoundType>> commands;

    private SoundTypeRegistry(final Supplier<Map<ResourceLocation, SoundType>> vanillaComputer) {
        this.soundTypes = HashBiMap.create();
        this.inverse = this.soundTypes.inverse();
        this.vanillaProvider = map -> map.putAll(vanillaComputer.get());
        this.commands = new HashMap<>();
    }

    public static SoundTypeRegistry of() {
        return new SoundTypeRegistry(SoundTypeRegistry::gatherVanilla);
    }

    private static Map<ResourceLocation, SoundType> gatherVanilla() {
        final Map<ResourceLocation, SoundType> map = new HashMap<>();
        entry(map, "wood", SoundType.WOOD);
        entry(map, "gravel", SoundType.GRAVEL);
        entry(map, "grass", SoundType.GRASS);
        entry(map, "lily_pad", SoundType.LILY_PAD);
        entry(map, "stone", SoundType.STONE);
        entry(map, "metal", SoundType.METAL);
        entry(map, "glass", SoundType.GLASS);
        entry(map, "wool", SoundType.WOOL);
        entry(map, "sand", SoundType.SAND);
        entry(map, "snow", SoundType.SNOW);
        entry(map, "powder_snow", SoundType.POWDER_SNOW);
        entry(map, "ladder", SoundType.LADDER);
        entry(map, "anvil", SoundType.ANVIL);
        entry(map, "slime_block", SoundType.SLIME_BLOCK);
        entry(map, "honey_block", SoundType.HONEY_BLOCK);
        entry(map, "wet_grass", SoundType.WET_GRASS);
        entry(map, "coral_block", SoundType.CORAL_BLOCK);
        entry(map, "bamboo", SoundType.BAMBOO);
        entry(map, "bamboo_sapling", SoundType.BAMBOO_SAPLING);
        entry(map, "scaffolding", SoundType.SCAFFOLDING);
        entry(map, "sweet_berry_bush", SoundType.SWEET_BERRY_BUSH);
        entry(map, "crop", SoundType.CROP);
        entry(map, "hard_crop", SoundType.HARD_CROP);
        entry(map, "vine", SoundType.VINE);
        entry(map, "nether_wart", SoundType.NETHER_WART);
        entry(map, "lantern", SoundType.LANTERN);
        entry(map, "stem", SoundType.STEM);
        entry(map, "nylium", SoundType.NYLIUM);
        entry(map, "fungus", SoundType.FUNGUS);
        entry(map, "roots", SoundType.ROOTS);
        entry(map, "shroomlight", SoundType.SHROOMLIGHT);
        entry(map, "weeping_vines", SoundType.WEEPING_VINES);
        entry(map, "twisting_vines", SoundType.TWISTING_VINES);
        entry(map, "soul_sand", SoundType.SOUL_SAND);
        entry(map, "soul_soil", SoundType.SOUL_SOIL);
        entry(map, "basalt", SoundType.BASALT);
        entry(map, "wart_block", SoundType.WART_BLOCK);
        entry(map, "netherrack", SoundType.NETHERRACK);
        entry(map, "nether_bricks", SoundType.NETHER_BRICKS);
        entry(map, "nether_sprouts", SoundType.NETHER_SPROUTS);
        entry(map, "nether_ore", SoundType.NETHER_ORE);
        entry(map, "bone_block", SoundType.BONE_BLOCK);
        entry(map, "netherite_block", SoundType.NETHERITE_BLOCK);
        entry(map, "ancient_debris", SoundType.ANCIENT_DEBRIS);
        entry(map, "lodestone", SoundType.LODESTONE);
        entry(map, "chain", SoundType.CHAIN);
        entry(map, "nether_gold_ore", SoundType.NETHER_GOLD_ORE);
        entry(map, "gilded_blackstone", SoundType.GILDED_BLACKSTONE);
        entry(map, "candle", SoundType.CANDLE);
        entry(map, "amethyst", SoundType.AMETHYST);
        entry(map, "amethyst_cluster", SoundType.AMETHYST_CLUSTER);
        entry(map, "small_amethyst_bud", SoundType.SMALL_AMETHYST_BUD);
        entry(map, "medium_amethyst_bud", SoundType.MEDIUM_AMETHYST_BUD);
        entry(map, "large_amethyst_bud", SoundType.LARGE_AMETHYST_BUD);
        entry(map, "tuff", SoundType.TUFF);
        entry(map, "calcite", SoundType.CALCITE);
        entry(map, "dripstone_block", SoundType.DRIPSTONE_BLOCK);
        entry(map, "pointed_dripstone", SoundType.POINTED_DRIPSTONE);
        entry(map, "copper", SoundType.COPPER);
        entry(map, "cave_vines", SoundType.CAVE_VINES);
        entry(map, "spore_blossom", SoundType.SPORE_BLOSSOM);
        entry(map, "azalea", SoundType.AZALEA);
        entry(map, "flowering_azalea", SoundType.FLOWERING_AZALEA);
        entry(map, "moss_carpet", SoundType.MOSS_CARPET);
        entry(map, "moss", SoundType.MOSS);
        entry(map, "big_dripleaf", SoundType.BIG_DRIPLEAF);
        entry(map, "small_dripleaf", SoundType.SMALL_DRIPLEAF);
        entry(map, "rooted_dirt", SoundType.ROOTED_DIRT);
        entry(map, "hanging_roots", SoundType.HANGING_ROOTS);
        entry(map, "azalea_leaves", SoundType.AZALEA_LEAVES);
        entry(map, "sculk_sensor", SoundType.SCULK_SENSOR);
        entry(map, "glow_lichen", SoundType.GLOW_LICHEN);
        entry(map, "deepslate", SoundType.DEEPSLATE);
        entry(map, "deepslate_bricks", SoundType.DEEPSLATE_BRICKS);
        entry(map, "deepslate_tiles", SoundType.DEEPSLATE_TILES);
        entry(map, "polished_deepslate", SoundType.POLISHED_DEEPSLATE);
        return map;
    }

    private static void entry(final Map<ResourceLocation, SoundType> map, final String name, final SoundType type) {
        map.put(new ResourceLocation(name.toLowerCase(Locale.ENGLISH)), type);
    }

    @Override
    public ObjectType<SoundType> type() {
        return VanillaObjectTypes.SOUND_TYPE;
    }

    @Override
    public SoundType get(final ResourceLocation name) {
        this.populateMap();

        if (this.commands.containsKey(name)) {
            return this.register(name, this.commands.get(name));
        }

        return this.soundTypes.get(name);
    }

    @Override
    public ResourceLocation nameOf(final SoundType type) {
        this.populateMap();

        return this.inverse.computeIfAbsent(type, it -> {
            throw new IllegalArgumentException("No such sound type " + it + " known: it is probably not registered");
        });
    }

    @Override
    public Collection<SoundType> all() {
        this.populateMap();
        return this.soundTypes.values();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<SoundType> objectCreator) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(objectCreator);
        if (this.commands.containsKey(name)) {
            throw new IllegalArgumentException("A sound type with name '" + name + "' was already enqueued for registration");
        }
        this.commands.put(name, objectCreator);
    }

    private void populateMap() {
        if (this.soundTypes.isEmpty()) {
            this.vanillaProvider.accept(this.soundTypes);
        }
    }

    public SoundType register(final ResourceLocation name, final Supplier<SoundType> creator) {
        Objects.requireNonNull(creator);
        if (this.soundTypes.containsKey(Objects.requireNonNull(name))) { // Sanity check, but this should never happen
            throw new IllegalArgumentException("A sound type with name '" + name + "' is already known");
        }
        final SoundType material = Objects.requireNonNull(creator.get());
        this.soundTypes.put(name, material);
        this.commands.remove(name);
        return material;
    }
}

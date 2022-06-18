package com.blamejared.contenttweaker.vanilla.api.zen.builder.block;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.zen.rt.FactoryMetaFactory;
import com.blamejared.contenttweaker.vanilla.api.resource.LootTable;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.item.BlockItemBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.BlockProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.BlockPropertyFunctions;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.SoundTypeReference;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorPropertiesAccessor;
import com.blamejared.contenttweaker.vanilla.zen.factory.ItemFactory;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.BLOCK_BUILDER_PACKAGE + ".BlockBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class BlockBuilder<T extends BlockBuilder<T>> {
    protected record GenerateFlags(boolean generateLootTable, boolean generateBlockItem) {}

    // Compare with equality
    private static final ResourceLocation DO_NOT_CLONE_DROPS = ContentTweakerConstants.rl("do_not_clone_drops");
    private static final ResourceLocation DO_NOT_DROP_SHIT = ContentTweakerConstants.rl("do_not_drop_shit");

    private final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager;

    private StandardBlockProperties cloningProperties;

    private MaterialReference material;
    private BlockPropertyFunctions.MaterialColorFinder materialColor;
    private Boolean hasCollision;
    private SoundTypeReference soundType;
    private BlockPropertyFunctions.LightLevelComputer lightEmission;
    private Float explosionResistance;
    private Float destroyTime;
    private Boolean requiresTool;
    private Boolean ticksRandomly;
    private Float friction;
    private Float speed;
    private Float jump;
    private ResourceLocation drops;
    private Boolean occlude;
    private Boolean air;
    // TODO("See StandardBlockProperties")
    private Boolean dynamic;

    private CreativeTabReference tab;
    private boolean blockItem;

    protected BlockBuilder(final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager) {
        this.registrationManager = Objects.requireNonNull(registrationManager);
        this.cloningProperties = null;
        this.material = null;
        this.materialColor = null;
        this.hasCollision = null;
        this.soundType = null;
        this.lightEmission = null;
        this.explosionResistance = null;
        this.destroyTime = null;
        this.requiresTool = null;
        this.ticksRandomly = null;
        this.friction = null;
        this.speed = null;
        this.jump = null;
        this.drops = null;
        this.occlude = null;
        this.air = null;
        // TODO("See StandardBlockProperties")
        this.dynamic = null;
        this.tab = null;
        this.blockItem = true;
    }

    @ZenCodeType.Method("cloning")
    public T cloning(final BlockProperties properties) {
        Objects.requireNonNull(properties);
        final StandardBlockProperties standard = "standard".equals(properties.type())? (StandardBlockProperties) properties : null;
        if (standard == null) {
            throw new IllegalArgumentException("Unknown set of properties " + properties.type() + " to clone from");
        }
        if (this.cloningProperties != null) {
            throw new IllegalStateException("Already specified properties to clone from");
        }
        this.cloningProperties = standard;
        return this.self();
    }

    @ZenCodeType.Method("material")
    public T material(final MaterialReference reference) {
        this.material = reference;
        return this.self();
    }

    @ZenCodeType.Method("overridingMaterialColor")
    public T overridingMaterialColor(final BlockPropertyFunctions.MaterialColorFinder finder) {
        this.materialColor = finder;
        return this.self();
    }

    @ZenCodeType.Method("hasCollisions")
    public T hasCollisions(final boolean collisions) {
        this.hasCollision = collisions;
        return this.self();
    }

    @ZenCodeType.Method("noCollisions")
    public T noCollisions() {
        return this.hasCollisions(false);
    }

    @ZenCodeType.Method("sound")
    public T sound(final SoundTypeReference reference) {
        this.soundType = reference;
        return this.self();
    }

    @ZenCodeType.Method("lightLevel")
    public T lightLevel(final BlockPropertyFunctions.LightLevelComputer lightLevelComputer) {
        this.lightEmission = lightLevelComputer;
        return this.self();
    }

    @ZenCodeType.Method("lightLevel")
    public T lightLevel(final int level) {
        if (level < 0 || level > 15) {
            throw new IllegalArgumentException("Invalid light level " + level + ": must be between 0 and 15");
        }
        return this.lightLevel(it -> level);
    }

    @ZenCodeType.Method("explosionResistance")
    public T explosionResistance(final float explosionResistance) {
        if (explosionResistance < 0.0F) {
            throw new IllegalArgumentException("Explosion resistance cannot be negative");
        }
        this.explosionResistance = explosionResistance;
        return this.self();
    }

    @ZenCodeType.Method("destroyTime")
    public T destroyTime(final float destroyTime) {
        this.destroyTime = destroyTime;
        return this.self();
    }

    @ZenCodeType.Method("strength")
    public T strength(final float destroyTime, final float explosionResistance) {
        return this.explosionResistance(explosionResistance).destroyTime(destroyTime);
    }

    @ZenCodeType.Method("strength")
    public T strength(final float strength) {
        return this.strength(strength, strength);
    }

    @ZenCodeType.Method("instabreak")
    public T breakInstantly() {
        return this.strength(0.0F);
    }

    @ZenCodeType.Method("requiresToolToDrop")
    public T requiresToolToDrop(final boolean requiresTool) {
        this.requiresTool = requiresTool;
        return this.self();
    }

    @ZenCodeType.Method("requiresToolToDrop")
    public T requiresToolToDrop() {
        return this.requiresToolToDrop(true);
    }

    @ZenCodeType.Method("ticksRandomly")
    public T ticksRandomly(final boolean ticksRandomly) {
        this.ticksRandomly = ticksRandomly;
        return this.self();
    }

    @ZenCodeType.Method("ticksRandomly")
    public T ticksRandomly() {
        return this.ticksRandomly(true);
    }

    @ZenCodeType.Method("friction")
    public T friction(final float friction) {
        this.friction = friction;
        return this.self();
    }

    @ZenCodeType.Method("speedFactor")
    public T speedFactor(final float speed) {
        this.speed = speed;
        return this.self();
    }

    @ZenCodeType.Method("jumpFactor")
    public T jumpFactor(final float jump) {
        this.jump = jump;
        return this.self();
    }

    @ZenCodeType.Method("dropsFrom")
    public T dropsFrom(final ResourceLocation drops) {
        this.drops = drops;
        return this.self();
    }

    @ZenCodeType.Method("dropsLike")
    public T dropsLike(final BlockReference reference) {
        return this.dropsFrom(reference.id());
    }

    @ZenCodeType.Method("noDrops")
    public T noDrops() {
        return this.dropsFrom(DO_NOT_DROP_SHIT);
    }

    @ZenCodeType.Method("dropsNormally")
    public T dropsNormally() {
        return this.cloningProperties != null? this.dropsFrom(DO_NOT_CLONE_DROPS) : this.self();
    }

    @ZenCodeType.Method("occludes")
    public T occludes(final boolean occlude) {
        this.occlude = occlude;
        return this.self();
    }

    @ZenCodeType.Method("noOcclusion")
    public T noOcclusion() {
        return this.occludes(false);
    }

    @ZenCodeType.Method("air")
    public T air(final boolean air) {
        this.air = air;
        return this.self();
    }

    @ZenCodeType.Method("air")
    public T air() {
        return this.air(true);
    }

    // TODO("See StandardBlockProperties")

    @ZenCodeType.Method("dynamicShape")
    public T dynamicShape(final boolean dynamic) {
        this.dynamic = dynamic;
        return this.self();
    }

    @ZenCodeType.Method("dynamicShape")
    public T dynamicShape() {
        return this.dynamicShape(true);
    }

    @ZenCodeType.Method("generateCorrespondingItem")
    public T generateCorrespondingItem(final boolean enable) {
        this.blockItem = enable;
        return this.self();
    }

    @ZenCodeType.Method("noCorrespondingItem")
    public T noCorrespondingItem() {
        return this.generateCorrespondingItem(false);
    }

    @ZenCodeType.Method("tab")
    public T tab(final CreativeTabReference reference) {
        this.tab = reference;
        return this.self();
    }

    @ZenCodeType.Method("build")
    public final BlockReference build(final String name) {
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final GenerateFlags flags = new GenerateFlags(this.drops == null, this.blockItem);
        final BlockReference reference = this.registrationManager.apply(this.create(id, this.checkAndMakeProperties(), flags), manager -> this.provideResources(id, manager, flags));
        if (flags.generateBlockItem()) {
            this.makeBlockItem(reference);
        }
        return reference;
    }

    public abstract ObjectHolder<? extends Block> create(final ResourceLocation name, final Supplier<BlockBehaviour.Properties> builtProperties, final GenerateFlags flags);

    public abstract void provideResources(final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags);

    protected final Optional<LootTable> emptyTable(final ResourceLocation name, final Function<ResourceLocation, String> messageSupplier) {
        final String message = Objects.requireNonNull(messageSupplier).apply(Objects.requireNonNull(name));
        if (message != null) {
            CraftTweakerAPI.LOGGER.warn(message);
        }
        return Optional.of(LootTable.ofBlock());
    }

    protected final Optional<LootTable> selfLootTable(final ResourceLocation name, final GenerateFlags flags) {
        Objects.requireNonNull(name);
        if (!Objects.requireNonNull(flags).generateBlockItem()) {
            return Optional.empty();
        }
        final LootTable table = LootTable.ofBlock()
                .pool()
                .rolls(1)
                .bonusRolls(0.0F)
                .conditionally(new ResourceLocation("survives_explosion"), new JsonObject())
                .entry(new ResourceLocation("item"))
                .name(name.toString())
                .finish()
                .finish();
        return Optional.of(table);
    }

    private Supplier<BlockBehaviour.Properties> checkAndMakeProperties() {
        return this.cloningProperties != null? this.checkAndMakeWithOverrides() : this.checkAndMakeQuick();
    }

    private Supplier<BlockBehaviour.Properties> checkAndMakeWithOverrides() {
        this.checkCommon();
        return this::makeWithOverrides;
    }

    private Supplier<BlockBehaviour.Properties> checkAndMakeQuick() {
        if (this.material == null) {
            throw new IllegalStateException("Unable to create a property instance without a material");
        }
        this.checkCommon();
        return this::makeQuick;
    }

    private void checkCommon() {
        if (this.hasCollision != null && !this.hasCollision && this.occlude != null && this.occlude) {
            throw new IllegalStateException("A block cannot both have no collisions and allow occlusion");
        }
        if (this.explosionResistance != null && this.explosionResistance < 0.0F) {
            throw new IllegalStateException("A block cannot have negative explosion resistance");
        }
    }

    private BlockBehaviour.Properties makeWithOverrides() {
        return this.make(
                this.material != null? this.material : this.cloningProperties.material(),
                this.materialColor != null? this.materialColor : this.cloningProperties.materialColor(),
                this.hasCollision != null? this.hasCollision : this.cloningProperties.hasCollision(),
                this.soundType != null? this.soundType : this.cloningProperties.soundType(),
                this.lightEmission != null? this.lightEmission : this.cloningProperties.lightEmission(),
                this.explosionResistance != null? this.explosionResistance : this.cloningProperties.explosionResistance(),
                this.destroyTime != null? this.destroyTime : this.cloningProperties.destroyTime(),
                this.requiresTool != null? this.requiresTool : this.cloningProperties.requiresCorrectToolForDrops(),
                this.ticksRandomly != null? this.ticksRandomly : this.cloningProperties.isRandomlyTicking(),
                this.friction != null? this.friction : this.cloningProperties.friction(),
                this.speed != null? this.speed : this.cloningProperties.speedFactor(),
                this.jump != null? this.jump : this.cloningProperties.jumpFactor(),
                this.drops != null? this.drops == DO_NOT_CLONE_DROPS? null : this.drops : this.cloningProperties.drops(),
                this.occlude != null? this.occlude : this.cloningProperties.canOcclude(),
                this.air != null? this.air : this.cloningProperties.isAir(),
                // TODO("See StandardBlockProperties")
                this.dynamic != null? this.dynamic : this.cloningProperties.dynamicShape()
        );
    }

    private BlockBehaviour.Properties makeQuick() {
        return this.make(
                this.material,
                this.materialColor,
                this.hasCollision,
                this.soundType,
                this.lightEmission,
                this.explosionResistance,
                this.destroyTime,
                this.requiresTool,
                this.ticksRandomly,
                this.friction,
                this.speed,
                this.jump,
                this.drops,
                this.occlude,
                this.air,
                // TODO("See StandardBlockProperties")
                this.dynamic
        );
    }

    private BlockBehaviour.Properties make(
            final MaterialReference materialReference,
            final BlockPropertyFunctions.MaterialColorFinder materialColorFunction,
            final Boolean hasCollision,
            final SoundTypeReference soundTypeReference,
            final BlockPropertyFunctions.LightLevelComputer lightEmissionFunction,
            final Float explosionResistance,
            final Float destroyTime,
            final Boolean requiresTool,
            final Boolean ticksRandomly,
            final Float friction,
            final Float speed,
            final Float jump,
            final ResourceLocation drops,
            final Boolean occlude,
            final Boolean air,
            // TODO("See StandardBlockProperties")
            final Boolean dynamic
    ) {
        final Material material = materialReference.unwrap();
        final Function<BlockState, MaterialColor> colorFunction = materialColorFunction == null? x -> material.getColor() : x -> materialColorFunction.colorOf(x).unwrap();
        final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(material, colorFunction);
        if (hasCollision != null && !hasCollision) {
            properties.noCollission();
        }
        if (soundTypeReference != null) {
            properties.sound(soundTypeReference.unwrap());
        }
        if (lightEmissionFunction != null) {
            properties.lightLevel(lightEmissionFunction::lightOf);
        }
        if (explosionResistance != null) {
            properties.explosionResistance(explosionResistance);
        }
        if (destroyTime != null) {
            properties.destroyTime(destroyTime);
        }
        if (requiresTool != null && requiresTool) {
            properties.requiresCorrectToolForDrops();
        }
        if (ticksRandomly != null && ticksRandomly) {
            properties.randomTicks();
        }
        if (friction != null) {
            properties.friction(friction);
        }
        if (speed != null) {
            properties.speedFactor(speed);
        }
        if (jump != null) {
            properties.jumpFactor(jump);
        }
        if (drops != null && drops != DO_NOT_CLONE_DROPS) {
            if (drops == DO_NOT_DROP_SHIT) {
                properties.noDrops();
            } else {
                // Ugly, but you can only drop like another block, not a custom RL
                ((BlockBehaviorPropertiesAccessor) properties).contenttweaker$drops(drops);
            }
        }
        if (occlude != null && !occlude) {
            properties.noOcclusion();
        }
        if (air != null && air) {
            properties.air();
        }
        // TODO("See StandardBlockProperties")
        if (dynamic != null && dynamic) {
            properties.dynamicShape();
        }
        return properties;
    }

    private void makeBlockItem(final BlockReference reference) {
        // TODO("This references non-api: fix")
        FactoryMetaFactory.factory(Item.class, ItemFactory.class, new ResourceLocation("item"))
                .typed(BlockItemBuilder.class)
                .block(reference)
                .tab(this.tab)
                .build(reference.id().getPath());
    }

    private T self() {
        return GenericUtil.uncheck(this);
    }
}

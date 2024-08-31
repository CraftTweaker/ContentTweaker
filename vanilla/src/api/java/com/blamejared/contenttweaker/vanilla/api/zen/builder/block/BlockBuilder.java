package com.blamejared.contenttweaker.vanilla.api.zen.builder.block;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.ContentTweakerLoggers;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.resource.LootTable;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.item.BlockItemBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.factory.ItemFactory;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.BlockProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.BlockPropertyFunctions;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.VarHandle;
import java.util.Objects;
import java.util.function.*;

@ZenCodeType.Name(ContentTweakerVanillaConstants.BLOCK_BUILDER_PACKAGE + ".BlockBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class BlockBuilder<T extends BlockBuilder<T>> {
    protected record GenerateFlags(boolean generateLootTable, boolean generateBlockItem) {}

    private static final class PropertySettingHandles {
        static final VarHandle DROPS = Handles.trusted().linkField(
                HandleAccess.directAccess(),
                BlockBehaviour.Properties.class,
                Handles.Names.of("field_10666", "f_60894_", "drops"),
                ResourceLocation.class
        );
        static final VarHandle REQUIRED_FEATURES = Handles.trusted().linkField(
                HandleAccess.directAccess(),
                BlockBehaviour.Properties.class,
                Handles.Names.of("field_40342", "f_244138_", "requiredFeatures"),
                FeatureFlagSet.class
        );

        private PropertySettingHandles() {}
    }

    private static final String LOOT_GEN_FAILURE_DUE_TO_NO_ITEM =
            "Unable to automatically generate loot table for block '{}' because the automatic block item has been disabled: an empty one will be generated instead";

    // Compare with equality
    private static final ResourceLocation DO_NOT_CLONE_DROPS = ContentTweakerConstants.rl("do_not_clone_drops");
    private static final ResourceLocation DO_NOT_DROP_DROPS = ContentTweakerConstants.rl("do_not_drop_drops");
    private static final ResourceLocation FORCE_GENERATION_OF_DROPS = ContentTweakerConstants.rl("force_generation_of_drops");

    private final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager;

    private StandardBlockProperties cloningProperties;

    private BlockPropertyFunctions.MapColorComputer mapColor;
    private Boolean hasCollision;
    private Reference<SoundType> soundType;
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
    private Boolean ignitedByLava;
    private Boolean liquid;
    private Boolean forceSolidOff;
    private Boolean forceSolidOn;
    private PushReaction pushReaction;
    private Boolean spawnBreakingParticles;
    private NoteBlockInstrument instrument;
    private Boolean replaceable;
    private BlockPropertyFunctions.ValidSpawnPredicate validSpawn;
    private BlockPropertyFunctions.SimpleStatePredicate redstoneConductor;
    private BlockPropertyFunctions.SimpleStatePredicate suffocating;
    private BlockPropertyFunctions.SimpleStatePredicate viewBlocking;
    private BlockPropertyFunctions.SimpleStatePredicate postProcess;
    private BlockPropertyFunctions.SimpleStatePredicate emissive;
    private Boolean dynamic;
    private FeatureFlagSet requiredFeatures;
    private BlockBehaviour.OffsetType offsetType;

    private boolean blockItem;

    protected BlockBuilder(final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager) {
        this.registrationManager = Objects.requireNonNull(registrationManager);
        this.cloningProperties = null;
        this.mapColor = null;
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
        this.ignitedByLava = null;
        this.liquid = null;
        this.forceSolidOff = null;
        this.forceSolidOn = null;
        this.pushReaction = null;
        this.spawnBreakingParticles = null;
        this.instrument = null;
        this.replaceable = null;
        this.validSpawn = null;
        this.redstoneConductor = null;
        this.suffocating = null;
        this.viewBlocking = null;
        this.postProcess = null;
        this.emissive = null;
        this.dynamic = null;
        this.requiredFeatures = null;
        this.offsetType = null;
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

    @ZenCodeType.Method("mapColor")
    public T mapColor(final BlockPropertyFunctions.MapColorComputer computer) {
        this.mapColor = computer;
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
    public T sound(final Reference<SoundType> reference) {
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

    @ZenCodeType.Method("breakInstantly")
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
        return this.dropsFrom(DO_NOT_DROP_DROPS);
    }

    @ZenCodeType.Method("dropsNormally")
    public T dropsNormally() {
        return this.cloningProperties != null? this.dropsFrom(DO_NOT_CLONE_DROPS) : this.self();
    }

    @ZenCodeType.Method("dropsItselfRegardless")
    public T dropsItselfRegardless() {
        return this.dropsFrom(FORCE_GENERATION_OF_DROPS);
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

    @ZenCodeType.Method("ignitedByLava")
    public T ignitedByLava(final boolean ignitedByLava) {
        this.ignitedByLava = ignitedByLava;
        return this.self();
    }

    @ZenCodeType.Method("ignitedByLava")
    public T ignitedByLava() {
        return this.ignitedByLava(true);
    }

    @Deprecated
    @ZenCodeType.Method("liquid")
    public T liquid(final boolean liquid) {
        this.liquid = liquid;
        return this.self();
    }

    @Deprecated
    @ZenCodeType.Method("liquid")
    public T liquid() {
        return this.liquid(true);
    }

    @Deprecated
    @ZenCodeType.Method("forceSolidOff")
    public T forceSolidOff(final boolean forceSolidOff) {
        this.forceSolidOff = forceSolidOff;
        return this.self();
    }

    @Deprecated
    @ZenCodeType.Method("forceSolidOff")
    public T forceSolidOff() {
        return this.forceSolidOff(true);
    }

    @ZenCodeType.Method("forceSolidOn")
    public T forceSolidOn(final boolean forceSolidOn) {
        this.forceSolidOn = true;
        return this.self();
    }

    @ZenCodeType.Method("forceSolidOn")
    public T forceSolidOn() {
        return this.forceSolidOn(true);
    }

    @ZenCodeType.Method("forceSolid")
    public T forceSolid(final boolean solid) {
        return solid? this.forceSolidOn() : this.forceSolidOff();
    }

    @ZenCodeType.Method("forceSolid")
    public T forceSolid() {
        return this.forceSolid(true);
    }

    @ZenCodeType.Method("pushReaction")
    public T pushReaction(final PushReaction pushReaction) {
        this.pushReaction = pushReaction;
        return this.self();
    }

    @ZenCodeType.Method("spawnBreakingParticles")
    public T spawnBreakingParticles(final boolean spawnBreakingParticles) {
        this.spawnBreakingParticles = spawnBreakingParticles;
        return this.self();
    }

    @ZenCodeType.Method("spawnBreakingParticles")
    public T spawnBreakingParticles() {
        return this.spawnBreakingParticles(true);
    }

    @ZenCodeType.Method("instrument")
    public T instrument(final NoteBlockInstrument instrument) {
        this.instrument = instrument;
        return this.self();
    }

    @ZenCodeType.Method("replaceable")
    public T replaceable(final boolean replaceable) {
        this.replaceable = replaceable;
        return this.self();
    }

    @ZenCodeType.Method("replaceable")
    public T replaceable() {
        return this.replaceable(true);
    }

    @ZenCodeType.Method("isValidSpawn")
    public T isValidSpawn(final BlockPropertyFunctions.ValidSpawnPredicate validSpawn) {
        this.validSpawn = validSpawn;
        return this.self();
    }

    @ZenCodeType.Method("isRedstoneConductor")
    public T isRedstoneConductor(final BlockPropertyFunctions.SimpleStatePredicate redstoneConductor) {
        this.redstoneConductor = redstoneConductor;
        return this.self();
    }

    @ZenCodeType.Method("isSuffocating")
    public T isSuffocating(final BlockPropertyFunctions.SimpleStatePredicate suffocating) {
        this.suffocating = suffocating;
        return this.self();
    }

    @ZenCodeType.Method("suffocate")
    public T suffocate() {
        return this.isSuffocating((state, getter, pos) -> true);
    }

    @ZenCodeType.Method("isViewBlocking")
    public T isViewBlocking(final BlockPropertyFunctions.SimpleStatePredicate viewBlocking) {
        this.viewBlocking = viewBlocking;
        return this.self();
    }

    @ZenCodeType.Method("blockView")
    public T blockView() {
        return this.isViewBlocking((state, getter, pos) -> true);
    }

    @ZenCodeType.Method("isSuffocatingAndViewBlocking")
    public T isSuffocatingAndViewBlocking(final BlockPropertyFunctions.SimpleStatePredicate predicate) {
        return this.isSuffocating(predicate).isViewBlocking(predicate);
    }

    @ZenCodeType.Method("suffocateAndBlockView")
    public T suffocateAndBlockView() {
        return this.suffocate().blockView();
    }

    @ZenCodeType.Method("isPostProcessingEnabled")
    public T isPostProcessingEnabled(final BlockPropertyFunctions.SimpleStatePredicate postProcess) {
        this.postProcess = postProcess;
        return this.self();
    }

    @ZenCodeType.Method("enablePostProcessing")
    public T enablePostProcessing() {
        return this.isPostProcessingEnabled((state, getter, pos) -> true);
    }

    @ZenCodeType.Method("isEmissive")
    public T isEmissive(final BlockPropertyFunctions.SimpleStatePredicate emissive) {
        this.emissive = emissive;
        return this.self();
    }

    @ZenCodeType.Method("emissive")
    public T emissive() {
        return this.isEmissive((state, getter, pos) -> true);
    }

    @ZenCodeType.Method("dynamicShape")
    public T dynamicShape(final boolean dynamic) {
        this.dynamic = dynamic;
        return this.self();
    }

    @ZenCodeType.Method("dynamicShape")
    public T dynamicShape() {
        return this.dynamicShape(true);
    }

    @ZenCodeType.Method("requiredFeatures")
    public T requiredFeatures(final FeatureFlagSet requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
        return this.self();
    }

    @ZenCodeType.Method("offsetType")
    public T offsetType(final BlockBehaviour.OffsetType offsetType) {
        this.offsetType = offsetType;
        return this.self();
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

    @ZenCodeType.Method("build")
    public final BlockReference build(final String name) {
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final GenerateFlags flags = this.flags();
        final BlockReference reference = this.registrationManager.apply(this.create(id, this.createPropertiesCreator(), flags), manager -> this.provideResources(id, manager, flags));
        if (flags.generateBlockItem()) {
            this.makeBlockItem(reference);
        }
        return reference;
    }

    protected abstract ObjectHolder<? extends Block> create(final ResourceLocation name, final Supplier<BlockBehaviour.Properties> builtProperties, final GenerateFlags flags);

    protected abstract void provideResources(final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags);

    protected final void generateTable(final ResourceLocation name, final GenerateFlags flags, final Consumer<LootTable> tableUser) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(flags);
        Objects.requireNonNull(tableUser);

        if (!flags.generateLootTable()) {
            return;
        }

        final boolean generateItemBlockTable = flags.generateBlockItem() || this.drops == FORCE_GENERATION_OF_DROPS;

        if (!generateItemBlockTable) {
            ContentTweakerLoggers.user().warn(LOOT_GEN_FAILURE_DUE_TO_NO_ITEM, name);
            tableUser.accept(LootTable.ofBlock());
            return;
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
        tableUser.accept(table);
    }

    private GenerateFlags flags() {
        final boolean generateLootTable = this.drops == null || this.drops == FORCE_GENERATION_OF_DROPS || this.drops == DO_NOT_CLONE_DROPS;
        return new GenerateFlags(generateLootTable, this.blockItem);
    }

    private Supplier<BlockBehaviour.Properties> createPropertiesCreator() {
        this.verifyProperties();
        return this::createOverriddenProperties;
    }

    private void verifyProperties() {
        if (this.hasCollision != null && !this.hasCollision && this.occlude != null && this.occlude) {
            throw new IllegalStateException("A block cannot both have no collisions and allow occlusion");
        }
        if (this.explosionResistance != null && this.explosionResistance < 0.0F) {
            throw new IllegalStateException("A block cannot have negative explosion resistance");
        }
    }

    @SuppressWarnings("deprecation")
    private BlockBehaviour.Properties createOverriddenProperties() {
        final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
        this.applyMapColor(this.mapColor, StandardBlockProperties::mapColor, properties::mapColor);
        this.applyBoolean(this.hasCollision, StandardBlockProperties::hasCollision, false, properties::noCollission);
        this.apply(this.soundType, StandardBlockProperties::soundType, properties::sound, Reference::get);
        this.apply(this.lightEmission, StandardBlockProperties::lightEmission, properties::lightLevel, it -> it::lightOf);
        this.apply(this.explosionResistance, StandardBlockProperties::explosionResistance, properties::explosionResistance);
        this.apply(this.destroyTime, StandardBlockProperties::destroyTime, properties::destroyTime);
        this.applyIfTrue(this.requiresTool, StandardBlockProperties::requiresCorrectToolForDrops, properties::requiresCorrectToolForDrops);
        this.applyIfTrue(this.ticksRandomly, StandardBlockProperties::isRandomlyTicking, properties::randomTicks);
        this.apply(this.friction, StandardBlockProperties::friction, properties::friction);
        this.apply(this.speed, StandardBlockProperties::speedFactor, properties::speedFactor);
        this.apply(this.jump, StandardBlockProperties::jumpFactor, properties::jumpFactor);
        this.applyDrops(this.drops, StandardBlockProperties::drops, properties);
        this.applyBoolean(this.occlude, StandardBlockProperties::canOcclude, false, properties::noOcclusion);
        this.applyIfTrue(this.air, StandardBlockProperties::isAir, properties::air);
        this.applyIfTrue(this.ignitedByLava, StandardBlockProperties::ignitedByLava, properties::ignitedByLava);
        this.applyIfTrue(this.liquid, StandardBlockProperties::liquid, properties::liquid);
        this.applyIfTrue(this.forceSolidOff, StandardBlockProperties::forceSolidOff, properties::forceSolidOff);
        this.applyIfTrue(this.forceSolidOn, StandardBlockProperties::forceSolidOn, properties::forceSolidOn);
        this.apply(this.pushReaction, StandardBlockProperties::pushReaction, properties::pushReaction);
        this.applyBoolean(this.spawnBreakingParticles, StandardBlockProperties::spawnParticlesOnBreak, false, properties::noParticlesOnBreak);
        this.apply(this.instrument, StandardBlockProperties::instrument, properties::instrument);
        this.applyIfTrue(this.replaceable, StandardBlockProperties::replaceable, properties::replaceable);
        this.applyValidSpawn(this.validSpawn, StandardBlockProperties::isValidSpawn, properties::isValidSpawn);
        this.applyPredicate(this.redstoneConductor, StandardBlockProperties::isRedstoneConductor, properties::isRedstoneConductor);
        this.applyPredicate(this.suffocating, StandardBlockProperties::isSuffocating, properties::isSuffocating);
        this.applyPredicate(this.viewBlocking, StandardBlockProperties::isViewBlocking, properties::isViewBlocking);
        this.applyPredicate(this.postProcess, StandardBlockProperties::hasPostProcess, properties::hasPostProcess);
        this.applyPredicate(this.emissive, StandardBlockProperties::emissiveRendering, properties::emissiveRendering);
        this.applyIfTrue(this.dynamic, StandardBlockProperties::dynamicShape, properties::dynamicShape);
        this.apply(this.requiredFeatures, StandardBlockProperties::requiredFeatures, it -> PropertySettingHandles.REQUIRED_FEATURES.set(properties, it));
        this.apply(this.offsetType, StandardBlockProperties::offsetType, properties::offsetType);
        return properties;
    }

    private void applyIfTrue(final Boolean property, final Predicate<StandardBlockProperties> getter, final Runnable runnable) {
        this.applyBoolean(property, getter, true, runnable);
    }

    private void applyBoolean(final Boolean property, final Predicate<StandardBlockProperties> getter, final boolean expect, final Runnable runnable) {
        this.apply(property, getter::test, it -> {
            if (it == expect) {
                runnable.run();
            }
        });
    }

    private void applyDrops(
            final ResourceLocation property,
            final Function<StandardBlockProperties, ResourceLocation> getter,
            final BlockBehaviour.Properties properties
    ) {
        this.apply(property, getter, it -> {
            if (it == DO_NOT_DROP_DROPS) {
                properties.noLootTable();
            } else if (it != DO_NOT_CLONE_DROPS && it != FORCE_GENERATION_OF_DROPS) {
                PropertySettingHandles.DROPS.set(properties, it);
            }
        });
    }

    private void applyMapColor(
            final BlockPropertyFunctions.MapColorComputer property,
            final Function<StandardBlockProperties, BlockPropertyFunctions.MapColorComputer> getter,
            final Consumer<Function<BlockState, MapColor>> consumer
    ) {
        this.apply(property, getter, consumer, it -> state -> it.mapColorOf(state).get());
    }

    private void applyValidSpawn(
            final BlockPropertyFunctions.ValidSpawnPredicate property,
            final Function<StandardBlockProperties, BlockPropertyFunctions.ValidSpawnPredicate> getter,
            final Consumer<BlockBehaviour.StateArgumentPredicate<EntityType<?>>> consumer
    ) {
        this.apply(property, getter, consumer, it -> (state, bGetter, pos, type) -> it.isValidSpawn(state, bGetter, pos,
                SimpleReference.of(VanillaObjectTypes.ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.getKey(type))));
    }

    private void applyPredicate(
            final BlockPropertyFunctions.SimpleStatePredicate property,
            final Function<StandardBlockProperties, BlockPropertyFunctions.SimpleStatePredicate> getter,
            final Consumer<BlockBehaviour.StatePredicate> consumer
    ) {
        this.apply(property, getter, consumer, it -> it::test);
    }

    private <P> void apply(final P property, final Function<StandardBlockProperties, P> getter, final Consumer<P> consumer) {
        this.apply(property, getter, consumer, Function.identity());
    }

    private <P, B> void apply(final P property, final Function<StandardBlockProperties, P> getter, final Consumer<B> consumer, final Function<P, B> converter) {
        final P target = this.determineProperty(property, getter);
        if (target != null) {
            consumer.accept(converter.apply(target));
        }
    }

    private <P> P determineProperty(final P property, final Function<StandardBlockProperties, P> getter) {
        if (property != null) {
            return property;
        }
        if (this.cloningProperties != null) {
            return getter.apply(this.cloningProperties);
        }
        return null;
    }

    private void makeBlockItem(final BlockReference reference) {
        ContentTweakerApi.get()
                .registry()
                .findObjectFactory(VanillaObjectTypes.ITEM, ItemFactory.class)
                .typed(BlockItemBuilder.class)
                .block(reference)
                .build(reference.id().getPath());
    }

    private T self() {
        return GenericUtil.uncheck(this);
    }
}

package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MapColorReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.VarHandle;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.StandardBlockProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class StandardBlockProperties extends BlockProperties {
    private static final class PropertyReferences {
        static final VarHandle PROPERTIES = hb(BlockBehaviour.Properties.class, "properties", "f_60439_", "field_23155");
        static final VarHandle HAS_COLLISION = hb(boolean.class, "hasCollision", "f_60443_", "field_23159");
        static final VarHandle SOUND_TYPE = hb(SoundType.class, "soundType", "f_60446_", "field_23162");
        static final VarHandle IS_RANDOMLY_TICKING = hb(boolean.class, "isRandomlyTicking", "f_60445_", "field_23161");
        static final VarHandle REQUIRED_FEATURES = hb(FeatureFlagSet.class, "requiredFeatures", "f_243733_", "field_40337");

        static final VarHandle MAP_COLOR = hp(Function.class, "mapColor", "f_283880_", "field_10662");
        static final VarHandle LIGHT_EMISSION = hp(ToIntFunction.class, "lightEmission", "f_60886_", "field_10663");
        static final VarHandle REQUIRES_CORRECT_TOOL_FOR_DROPS = hp(boolean.class, "requiresCorrectToolForDrops", "f_60889_", "field_25185");
        static final VarHandle CAN_OCCLUDE = hp(boolean.class, "canOcclude", "f_60895_", "field_20721");
        static final VarHandle IS_AIR = hp(boolean.class, "isAir", "f_60896_", "field_23180");
        static final VarHandle IGNITED_BY_LAVA = hp(boolean.class, "ignitedByLava", "f_278123_", "field_43394");
        static final VarHandle LIQUID = hp(boolean.class, "liquid", "f_278418_", "field_44481");
        static final VarHandle FORCE_SOLID_OFF = hp(boolean.class, "forceSolidOff", "f_279665_", "field_44627");
        static final VarHandle FORCE_SOLID_ON = hp(boolean.class, "forceSolidOn", "f_279618_", "field_44628");
        static final VarHandle PUSH_REACTION = hp(PushReaction.class, "pushReaction", "f_278130_", "field_43395");
        static final VarHandle SPAWN_PARTICLES_ON_BREAK = hp(boolean.class, "spawnParticlesOnBreak", "f_243850_", "field_40341");
        static final VarHandle INSTRUMENT = hp(NoteBlockInstrument.class, "instrument", "f_279538_", "field_44629");
        static final VarHandle REPLACEABLE = hp(boolean.class, "replaceable", "f_279630_", "field_44630");
        static final VarHandle IS_VALID_SPAWN = hp(BlockBehaviour.StateArgumentPredicate.class, "isValidSpawn", "f_60897_", "field_23181");
        static final VarHandle IS_REDSTONE_CONDUCTOR = hp(BlockBehaviour.StatePredicate.class, "isRedstoneConductor", "f_60898_", "field_23182");
        static final VarHandle IS_SUFFOCATING = hp(BlockBehaviour.StatePredicate.class, "isSuffocating", "f_60899_", "field_23183");
        static final VarHandle IS_VIEW_BLOCKING = hp(BlockBehaviour.StatePredicate.class, "isViewBlocking", "f_60900_", "field_23184");
        static final VarHandle HAS_POST_PROCESS = hp(BlockBehaviour.StatePredicate.class, "hasPostProcess", "f_60901_", "field_23185");
        static final VarHandle EMISSIVE_RENDERING = hp(BlockBehaviour.StatePredicate.class, "emissiveRendering", "f_60902_", "field_23186");
        static final VarHandle OFFSET_FUNCTION = hp(Optional.class, "offsetFunction", "f_271289_", "field_42818");

        private PropertyReferences() {}

        static <T> T resolve(final VarHandle handle, final Object owner) {
            return GenericUtil.uncheck(handle.get(owner));
        }

        private static VarHandle hb(final Class<?> type, final String moj, final String srg, final String intermediary) {
            return h(BlockBehaviour.class, type, moj, srg, intermediary);
        }

        private static VarHandle hp(final Class<?> type, final String moj, final String srg, final String intermediary) {
            return h(BlockBehaviour.Properties.class, type, moj, srg, intermediary);
        }

        private static VarHandle h(final Class<?> owner, final Class<?> type, final String moj, final String srg, final String intermediary) {
            return Handles.trusted().linkField(HandleAccess.directAccess(), owner, Handles.Names.of(intermediary, srg, moj), type);
        }
    }

    private static final class HackyOffsetTypeHelperFields {
        private static final Supplier<BlockBehaviour.OffsetFunction> XZ_FUNCTION = Suppliers.memoize(() -> {
            final ResourceLocation mangrovePropaguleName = BuiltInRegistries.BLOCK.getKey(Blocks.MANGROVE_PROPAGULE);
            final BlockReference mangrovePropaguleReference = BlockReference.of(mangrovePropaguleName);
            final StandardBlockProperties properties = mangrovePropaguleReference.properties();
            final BlockBehaviour.Properties resolvedProperties = properties.resolveProperties();
            final Optional<BlockBehaviour.OffsetFunction> function = PropertyReferences.resolve(PropertyReferences.OFFSET_FUNCTION, resolvedProperties);
            assert function.isPresent();
            return function.get();
        });

        private HackyOffsetTypeHelperFields() {}

        static boolean isXz(final BlockBehaviour.OffsetFunction function) {
            // Here we are leveraging the fact that blocks cannot specify their own offset type, rather they have to
            // go through an enum. Leveraging the fact that Java creates one class for every lambda **and** does not
            // reuse them, this should be a good enough guess. Obviously, this must be periodically verified.
            return Objects.equals(function.getClass(), XZ_FUNCTION.get().getClass());
        }
    }

    public StandardBlockProperties(final BlockReference reference) {
        super(reference, "standard");
    }

    public BlockPropertyFunctions.MapColorComputer mapColor() {
        final Function<BlockState, MapColor> mapColor = PropertyReferences.resolve(PropertyReferences.MAP_COLOR, this.resolveProperties());
        return it -> MapColorReference.of(this.nameOf(VanillaObjectTypes.MAP_COLOR, mapColor.apply(it)));
    }

    public boolean hasCollision() {
        return PropertyReferences.resolve(PropertyReferences.HAS_COLLISION, this.resolve());
    }

    public Reference<SoundType> soundType() {
        final SoundType type = PropertyReferences.resolve(PropertyReferences.SOUND_TYPE, this.resolveProperties());
        return SimpleReference.of(VanillaObjectTypes.SOUND_TYPE, this.nameOf(VanillaObjectTypes.SOUND_TYPE, type));
    }

    public BlockPropertyFunctions.LightLevelComputer lightEmission() {
        final ToIntFunction<BlockState> lightEmission = PropertyReferences.resolve(PropertyReferences.LIGHT_EMISSION, this.resolveProperties());
        return lightEmission::applyAsInt;
    }

    public float explosionResistance() {
        return this.resolve().getExplosionResistance();
    }

    public float destroyTime() {
        return this.resolve().defaultDestroyTime();
    }

    public boolean requiresCorrectToolForDrops() {
        return PropertyReferences.resolve(PropertyReferences.REQUIRES_CORRECT_TOOL_FOR_DROPS, this.resolveProperties());
    }

    public boolean isRandomlyTicking() {
        return PropertyReferences.resolve(PropertyReferences.IS_RANDOMLY_TICKING, this.resolveProperties());
    }

    public float friction() {
        return this.resolve().getFriction();
    }

    public float speedFactor() {
        return this.resolve().getSpeedFactor();
    }

    public float jumpFactor() {
        return this.resolve().getJumpFactor();
    }

    public ResourceLocation drops() {
        return this.resolve().getLootTable();
    }

    public boolean canOcclude() {
        return PropertyReferences.resolve(PropertyReferences.CAN_OCCLUDE, this.resolveProperties());
    }

    public boolean isAir() {
        return PropertyReferences.resolve(PropertyReferences.IS_AIR, this.resolveProperties());
    }

    public boolean ignitedByLava() {
        return PropertyReferences.resolve(PropertyReferences.IGNITED_BY_LAVA, this.resolveProperties());
    }

    public boolean liquid() {
        return PropertyReferences.resolve(PropertyReferences.LIQUID, this.resolveProperties());
    }

    public boolean forceSolidOff() {
        return PropertyReferences.resolve(PropertyReferences.FORCE_SOLID_OFF, this.resolveProperties());
    }

    public boolean forceSolidOn() {
        return PropertyReferences.resolve(PropertyReferences.FORCE_SOLID_ON, this.resolveProperties());
    }

    public PushReaction pushReaction() {
        return PropertyReferences.resolve(PropertyReferences.PUSH_REACTION, this.resolveProperties());
    }

    public boolean spawnParticlesOnBreak() {
        return PropertyReferences.resolve(PropertyReferences.SPAWN_PARTICLES_ON_BREAK, this.resolveProperties());
    }

    public NoteBlockInstrument instrument() {
        return PropertyReferences.resolve(PropertyReferences.INSTRUMENT, this.resolveProperties());
    }

    public boolean replaceable() {
        return PropertyReferences.resolve(PropertyReferences.REPLACEABLE, this.resolveProperties());
    }

    public BlockPropertyFunctions.ValidSpawnPredicate isValidSpawn() {
        final BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn = PropertyReferences.resolve(PropertyReferences.IS_VALID_SPAWN, this.resolveProperties());
        return (state, getter, pos, entityTypeReference) -> isValidSpawn.test(state, getter, pos, entityTypeReference.get());
    }

    public BlockPropertyFunctions.SimpleStatePredicate isRedstoneConductor() {
        final BlockBehaviour.StatePredicate isRedstoneConductor = PropertyReferences.resolve(PropertyReferences.IS_REDSTONE_CONDUCTOR, this.resolveProperties());
        return isRedstoneConductor::test;
    }

    public BlockPropertyFunctions.SimpleStatePredicate isSuffocating() {
        final BlockBehaviour.StatePredicate isSuffocating = PropertyReferences.resolve(PropertyReferences.IS_SUFFOCATING, this.resolveProperties());
        return isSuffocating::test;
    }

    public BlockPropertyFunctions.SimpleStatePredicate isViewBlocking() {
        final BlockBehaviour.StatePredicate isViewBlocking = PropertyReferences.resolve(PropertyReferences.IS_VIEW_BLOCKING, this.resolveProperties());
        return isViewBlocking::test;
    }

    public BlockPropertyFunctions.SimpleStatePredicate hasPostProcess() {
        final BlockBehaviour.StatePredicate hasPostProcess = PropertyReferences.resolve(PropertyReferences.HAS_POST_PROCESS, this.resolveProperties());
        return hasPostProcess::test;
    }

    public BlockPropertyFunctions.SimpleStatePredicate emissiveRendering() {
        final BlockBehaviour.StatePredicate emissiveRendering = PropertyReferences.resolve(PropertyReferences.EMISSIVE_RENDERING, this.resolveProperties());
        return emissiveRendering::test;
    }

    public boolean dynamicShape() {
        return this.resolve().hasDynamicShape();
    }

    public FeatureFlagSet requiredFeatures() {
        return PropertyReferences.resolve(PropertyReferences.REQUIRED_FEATURES, this.resolve());
    }

    public BlockBehaviour.OffsetType offsetType() {
        final Optional<BlockBehaviour.OffsetFunction> offsetFunction = PropertyReferences.resolve(PropertyReferences.OFFSET_FUNCTION, this.resolveProperties());
        return offsetFunction.map(it -> HackyOffsetTypeHelperFields.isXz(it)? BlockBehaviour.OffsetType.XZ : BlockBehaviour.OffsetType.XYZ)
                .orElse(BlockBehaviour.OffsetType.NONE);
    }

    private BlockBehaviour.Properties resolveProperties() {
        return PropertyReferences.resolve(PropertyReferences.PROPERTIES, this.resolve());
    }

    private <T> ResourceLocation nameOf(final ObjectType<T> type, final T thing) {
        return ContentTweakerApi.get().registry().findResolver(type).nameOf(thing);
    }
}

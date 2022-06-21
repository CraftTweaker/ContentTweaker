package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MaterialColorReference;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorAccessor;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorPropertiesAccessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.StandardBlockProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class StandardBlockProperties extends BlockProperties {
    public StandardBlockProperties(final BlockReference reference) {
        super(reference, "standard");
    }

    public SimpleReference<Material> material() {
        final Material material = this.resolveProperties().contenttweaker$material();
        return SimpleReference.of(VanillaObjectTypes.MATERIAL, this.nameOf(VanillaObjectTypes.MATERIAL, material));
    }

    public BlockPropertyFunctions.MaterialColorFinder materialColor() {
        return this.resolveProperties()
                .contenttweaker$materialColor()
                .andThen(it -> this.nameOf(VanillaObjectTypes.MATERIAL_COLOR, it))
                .andThen(MaterialColorReference::of)::apply;
    }

    public boolean hasCollision() {
        return this.resolveProperties().contenttweaker$hasCollision();
    }

    public SimpleReference<SoundType> soundType() {
        final SoundType type = this.resolveProperties().contenttweaker$soundType();
        return SimpleReference.of(VanillaObjectTypes.SOUND_TYPE, this.nameOf(VanillaObjectTypes.SOUND_TYPE, type));
    }

    public BlockPropertyFunctions.LightLevelComputer lightEmission() {
        return this.resolveProperties().contenttweaker$lightEmission()::applyAsInt;
    }

    public float explosionResistance() {
        return this.resolve().getExplosionResistance();
    }

    public float destroyTime() {
        return this.resolve().defaultDestroyTime();
    }

    public boolean requiresCorrectToolForDrops() {
        return this.resolveProperties().contenttweaker$requiresCorrectToolForDrops();
    }

    public boolean isRandomlyTicking() {
        return this.resolveProperties().contenttweaker$isRandomlyTicking();
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
        return this.resolveProperties().contenttweaker$canOcclude();
    }

    public boolean isAir() {
        return this.resolveProperties().contenttweaker$isAir();
    }

    // TODO("Figure out all the following, because it requires Level access, for some reason")
    /*
      BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn = ($$0x, $$1x, $$2, $$3) -> {
         return $$0x.isFaceSturdy($$1x, $$2, Direction.UP) && $$0x.getLightEmission() < 14;
      };
      BlockBehaviour.StatePredicate isRedstoneConductor = ($$0x, $$1x, $$2) -> {
         return $$0x.getMaterial().isSolidBlocking() && $$0x.isCollisionShapeFullBlock($$1x, $$2);
      };
      BlockBehaviour.StatePredicate isSuffocating = ($$0x, $$1x, $$2) -> {
         return this.material.blocksMotion() && $$0x.isCollisionShapeFullBlock($$1x, $$2);
      };
      BlockBehaviour.StatePredicate isViewBlocking = this.isSuffocating;
      BlockBehaviour.StatePredicate hasPostProcess = ($$0x, $$1x, $$2) -> {
         return false;
      };
      BlockBehaviour.StatePredicate emissiveRendering = ($$0x, $$1x, $$2) -> {
         return false;
      };
     */

    public boolean dynamicShape() {
        return this.resolve().hasDynamicShape();
    }

    private BlockBehaviorPropertiesAccessor resolveProperties() {
        return ((BlockBehaviorPropertiesAccessor) ((BlockBehaviorAccessor) this.resolve()).contenttweaker$properties());
    }

    private <T> ResourceLocation nameOf(final ObjectType<T> type, final T thing) {
        return ContentTweakerApi.get().findResolver(type).nameOf(thing);
    }
}

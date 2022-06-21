package com.blamejared.contenttweaker.vanilla;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.plugin.FactoryMappingRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.core.api.plugin.RegistryResolverRegistration;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.registry.CreativeTabRegistry;
import com.blamejared.contenttweaker.vanilla.api.registry.MaterialColorRegistry;
import com.blamejared.contenttweaker.vanilla.api.registry.MaterialRegistry;
import com.blamejared.contenttweaker.vanilla.api.registry.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MaterialColorReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.contenttweaker.vanilla.zen.bracket.ContentTweakerVanillaBrackets;
import com.blamejared.contenttweaker.vanilla.zen.factory.BlockFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.CreativeTabFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.ItemFactory;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.zen.factory.MaterialColorFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.MaterialFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.SoundEventFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.SoundTypeFactory;
import com.blamejared.contenttweaker.vanilla.zen.factory.TierFactory;
import com.google.gson.reflect.TypeToken;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":vanilla")
public final class ContentTweakerVanillaPlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerObjectTypes(final ObjectTypeRegistration registration) {
        registration.registerType(VanillaObjectTypes.BLOCK);
        registration.registerType(VanillaObjectTypes.CREATIVE_TAB);
        registration.registerType(VanillaObjectTypes.ITEM);
        registration.registerType(VanillaObjectTypes.MATERIAL);
        registration.registerType(VanillaObjectTypes.MATERIAL_COLOR);
        registration.registerType(VanillaObjectTypes.SOUND_EVENT);
        registration.registerType(VanillaObjectTypes.SOUND_TYPE);
        registration.registerType(VanillaObjectTypes.TIER);
    }

    @Override
    public void registerFactoryMappings(final FactoryMappingRegistration registration) {
        registration.registerMapping(VanillaObjectTypes.BLOCK, BlockFactory.class);
        registration.registerMapping(VanillaObjectTypes.CREATIVE_TAB, CreativeTabFactory.class);
        registration.registerMapping(VanillaObjectTypes.ITEM, ItemFactory.class);
        registration.registerMapping(VanillaObjectTypes.MATERIAL, MaterialFactory.class);
        registration.registerMapping(VanillaObjectTypes.MATERIAL_COLOR, MaterialColorFactory.class);
        registration.registerMapping(VanillaObjectTypes.SOUND_EVENT, SoundEventFactory.class);
        registration.registerMapping(VanillaObjectTypes.SOUND_TYPE, SoundTypeFactory.class);
        registration.registerMapping(VanillaObjectTypes.TIER, TierFactory.class);
    }

    @Override
    @SuppressWarnings("Convert2Diamond") // This breaks javac （＾－＾）
    public void registerReferenceFactories(final ReferenceFactoryRegistration registration) {
        registration.register(VanillaObjectTypes.BLOCK, ReferenceFactory.of(new TypeToken<BlockReference>() {}, BlockReference::of));
        registration.register(VanillaObjectTypes.CREATIVE_TAB, ReferenceFactory.of(new TypeToken<CreativeTabReference>() {}, CreativeTabReference::of));
        registration.register(VanillaObjectTypes.ITEM, ReferenceFactory.of(new TypeToken<ItemReference>() {}, ItemReference::of));
        registration.register(VanillaObjectTypes.MATERIAL, ReferenceFactory.of(new TypeToken<SimpleReference<Material>>() {}, SimpleReference::of));
        registration.register(VanillaObjectTypes.MATERIAL_COLOR, ReferenceFactory.of(new TypeToken<MaterialColorReference>() {}, MaterialColorReference::of));
        registration.register(VanillaObjectTypes.SOUND_EVENT, ReferenceFactory.of(new TypeToken<SimpleReference<SoundEvent>>() {}, SimpleReference::of));
        registration.register(VanillaObjectTypes.SOUND_TYPE, ReferenceFactory.of(new TypeToken<SimpleReference<SoundType>>() {}, SimpleReference::of));
        registration.register(VanillaObjectTypes.TIER, ReferenceFactory.of(new TypeToken<TierReference>() {}, TierReference::of));
    }

    @Override
    public void registerResolvers(final RegistryResolverRegistration registration) {
        registration.register(VanillaObjectTypes.BLOCK, RegistryResolver.of(VanillaObjectTypes.BLOCK));
        registration.register(VanillaObjectTypes.CREATIVE_TAB, RegistryResolver.of(VanillaObjectTypes.CREATIVE_TAB, CreativeTabRegistry::of));
        registration.register(VanillaObjectTypes.ITEM, RegistryResolver.of(VanillaObjectTypes.ITEM));
        registration.register(VanillaObjectTypes.MATERIAL, RegistryResolver.of(VanillaObjectTypes.MATERIAL, MaterialRegistry::of));
        registration.register(VanillaObjectTypes.MATERIAL_COLOR, RegistryResolver.of(VanillaObjectTypes.MATERIAL_COLOR, MaterialColorRegistry::of));
        registration.register(VanillaObjectTypes.SOUND_EVENT, RegistryResolver.of(VanillaObjectTypes.SOUND_EVENT));
        registration.register(VanillaObjectTypes.SOUND_TYPE, RegistryResolver.of(VanillaObjectTypes.SOUND_TYPE, SoundTypeRegistry::of));
        // The tier resolver will be registered by the Forge/Fabric specific integration due to the different systems
    }

    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerVanillaBrackets.register(registration);
    }
}

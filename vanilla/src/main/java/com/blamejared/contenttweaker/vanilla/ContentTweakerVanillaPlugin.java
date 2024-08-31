package com.blamejared.contenttweaker.vanilla;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectFactoryRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.core.api.plugin.RegistryResolverRegistration;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.registry.MapColorRegistry;
import com.blamejared.contenttweaker.vanilla.api.registry.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.factory.*;
import com.blamejared.contenttweaker.vanilla.api.zen.object.*;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.zen.bracket.ContentTweakerVanillaBrackets;
import com.google.gson.reflect.TypeToken;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":vanilla")
public final class ContentTweakerVanillaPlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerObjectTypes(final ObjectTypeRegistration registration) {
        registration.registerType(VanillaObjectTypes.BLOCK);
        registration.registerType(VanillaObjectTypes.CREATIVE_TAB);
        registration.registerType(VanillaObjectTypes.ENTITY_TYPE);
        registration.registerType(VanillaObjectTypes.ITEM);
        registration.registerType(VanillaObjectTypes.MAP_COLOR);
        registration.registerType(VanillaObjectTypes.SOUND_EVENT);
        registration.registerType(VanillaObjectTypes.SOUND_TYPE);
        registration.registerType(VanillaObjectTypes.TIER);
    }

    @Override
    public void registerObjectFactories(final ObjectFactoryRegistration registration) {
        registration.registerFactory(VanillaObjectTypes.BLOCK, new BlockFactory());
        registration.registerFactory(VanillaObjectTypes.CREATIVE_TAB, new CreativeTabFactory());
        registration.registerFactory(VanillaObjectTypes.ITEM, new ItemFactory());
        registration.registerFactory(VanillaObjectTypes.MAP_COLOR, new MapColorFactory());
        registration.registerFactory(VanillaObjectTypes.SOUND_EVENT, new SoundEventFactory());
        registration.registerFactory(VanillaObjectTypes.SOUND_TYPE, new SoundTypeFactory());
        registration.registerFactory(VanillaObjectTypes.TIER, new TierFactory());
    }

    @Override
    @SuppressWarnings("Convert2Diamond") // This breaks javac （＾－＾）
    public void registerReferenceFactories(final ReferenceFactoryRegistration registration) {
        registration.register(VanillaObjectTypes.BLOCK, ReferenceFactory.of(new TypeToken<BlockReference>() {}, BlockReference::of));
        registration.register(VanillaObjectTypes.CREATIVE_TAB, ReferenceFactory.of(new TypeToken<CreativeTabReference>() {}, CreativeTabReference::of));
        registration.register(VanillaObjectTypes.ITEM, ReferenceFactory.of(new TypeToken<ItemReference>() {}, ItemReference::of));
        registration.register(VanillaObjectTypes.MAP_COLOR, ReferenceFactory.of(new TypeToken<MapColorReference>() {}, MapColorReference::of));
        registration.register(VanillaObjectTypes.SOUND_EVENT, ReferenceFactory.of(new TypeToken<SimpleReference<SoundEvent>>() {}, SimpleReference::of));
        registration.register(VanillaObjectTypes.SOUND_TYPE, ReferenceFactory.of(new TypeToken<SimpleReference<SoundType>>() {}, SimpleReference::of));
        registration.register(VanillaObjectTypes.TIER, ReferenceFactory.of(new TypeToken<TierReference>() {}, TierReference::of));
    }

    @Override
    public void registerResolvers(final RegistryResolverRegistration registration) {
        registration.register(VanillaObjectTypes.BLOCK, RegistryResolver.of(VanillaObjectTypes.BLOCK));
        registration.register(VanillaObjectTypes.CREATIVE_TAB, RegistryResolver.of(VanillaObjectTypes.CREATIVE_TAB));
        registration.register(VanillaObjectTypes.ENTITY_TYPE, RegistryResolver.of(VanillaObjectTypes.ENTITY_TYPE));
        registration.register(VanillaObjectTypes.ITEM, RegistryResolver.of(VanillaObjectTypes.ITEM));
        registration.register(VanillaObjectTypes.MAP_COLOR, RegistryResolver.of(VanillaObjectTypes.MAP_COLOR, MapColorRegistry::of));
        registration.register(VanillaObjectTypes.SOUND_EVENT, RegistryResolver.of(VanillaObjectTypes.SOUND_EVENT));
        registration.register(VanillaObjectTypes.SOUND_TYPE, RegistryResolver.of(VanillaObjectTypes.SOUND_TYPE, SoundTypeRegistry::of));
        // The tier resolver will be registered by the Forge/Fabric specific integration due to the different systems
    }

    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerVanillaBrackets.register(registration);
    }
}

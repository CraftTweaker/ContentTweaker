package com.blamejared.contenttweaker.vanilla;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.plugin.FactoryMappingRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.vanilla.api.zen.object.SoundEventReference;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.zen.bracket.ContentTweakerVanillaBrackets;
import com.blamejared.contenttweaker.vanilla.zen.factory.ItemFactory;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.zen.factory.SoundEventFactory;
import com.google.gson.reflect.TypeToken;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":vanilla")
public final class ContentTweakerVanillaPlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerObjectTypes(final ObjectTypeRegistration registration) {
        registration.registerType(VanillaObjectTypes.ITEM);
        registration.registerType(VanillaObjectTypes.SOUND_EVENT);
    }

    @Override
    public void registerFactoryMappings(final FactoryMappingRegistration registration) {
        registration.registerMapping(VanillaObjectTypes.ITEM, ItemFactory.class);
        registration.registerMapping(VanillaObjectTypes.SOUND_EVENT, SoundEventFactory.class);
    }

    @Override
    @SuppressWarnings("Convert2Diamond") // This breaks javac （＾－＾）
    public void registerReferenceFactories(final ReferenceFactoryRegistration registration) {
        registration.register(VanillaObjectTypes.ITEM, ReferenceFactory.of(new TypeToken<ItemReference>() {}, ItemReference::of));
        registration.register(VanillaObjectTypes.SOUND_EVENT, ReferenceFactory.of(new TypeToken<SoundEventReference>() {}, SoundEventReference::of));
    }

    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerVanillaBrackets.register(registration);
    }
}

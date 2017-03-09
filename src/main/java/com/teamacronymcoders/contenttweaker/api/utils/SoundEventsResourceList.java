package com.teamacronymcoders.contenttweaker.api.utils;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SoundEventsResourceList extends ResourceList<SoundEvent> {
    public SoundEventsResourceList() {
        super(SoundEvent.class, SoundEvents.class);
    }

    public void addResource(String name, SoundEvent resource) {
        ResourceLocation resourceLocation = ReflectionHelper.getPrivateValue(SoundEvent.class, null, "soundName", "field_187506_b");
        resources.put(resourceLocation.toString(), resource);
    }
}

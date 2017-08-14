package com.teamacronymcoders.contenttweaker.api.utils;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundEventsResourceList extends ResourceList<SoundEvent> {
    public SoundEventsResourceList() {
        super(SoundEvent.class, SoundEvents.class);
    }

    public void addResource(String name, SoundEvent resource) {

    }

    public SoundEvent getResource(String name) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(name));
    }

}

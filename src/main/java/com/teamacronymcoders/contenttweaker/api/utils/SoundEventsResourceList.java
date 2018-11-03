package com.teamacronymcoders.contenttweaker.api.utils;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SoundEventsResourceList extends ResourceList<SoundEvent> {
    public SoundEventsResourceList() {
        super(SoundEvent.class, SoundEvents.class);
    }

    @Override
    public void addResource(String name, SoundEvent resource) {

    }

    @Override
    public SoundEvent getResource(String name) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(name));
    }

    @Override
    public List<String> getAllNames() {
        return ForgeRegistries.SOUND_EVENTS.getKeys().stream().map(ResourceLocation::toString).collect(Collectors.toList());
    }

    @Override
    public Collection<SoundEvent> getAllResources() {
        return ForgeRegistries.SOUND_EVENTS.getValuesCollection();
    }
}

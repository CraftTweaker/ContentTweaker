package com.teamacronymcoders.contenttweaker.api.deserializer;


import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterDeserializerEvent extends Event {
    private DeserializerRegistry registry;

    public RegisterDeserializerEvent(DeserializerRegistry registry) {
        this.registry = registry;
    }

    public DeserializerRegistry getRegistry() {
        return this.registry;
    }

    public void register(IDeserializer deserializer) {
        this.registry.registerDeserializer(deserializer);
    }
}

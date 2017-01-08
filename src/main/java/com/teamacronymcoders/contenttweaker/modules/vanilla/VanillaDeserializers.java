package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.api.deserializer.DeserializerBase;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;

public class VanillaDeserializers {
    public void registerDeserializers(RegisterDeserializerEvent event) {
        event.register(new DeserializerBase<>("Block", BlockRepresentation.class, blockRepresentation -> true));
    }
}

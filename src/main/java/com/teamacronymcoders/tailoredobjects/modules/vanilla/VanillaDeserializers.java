package com.teamacronymcoders.tailoredobjects.modules.vanilla;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerBase;
import com.teamacronymcoders.tailoredobjects.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockRepresentation;

public class VanillaDeserializers {
    public void registerDeserializers(RegisterDeserializerEvent event) {
        event.register(new DeserializerBase<>("Block", BlockRepresentation.class, blockRepresentation -> true));
    }
}

package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.api.deserializer.DeserializerBase;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabContent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VanillaDeserializers {
    @SubscribeEvent
    public void registerDeserializers(RegisterDeserializerEvent event) {
        event.register(new DeserializerBase<>("Block", BlockRepresentation.class, BlockRepresentation::register));
        event.register(new DeserializerBase<>("CreativeTab", CreativeTabRepresentation.class, true,
                CreativeTabRepresentation::register));
        event.register(new DeserializerBase<>("Item", ItemRepresentation.class, true,
                ItemRepresentation::register));
    }
}

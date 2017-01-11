package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.contenttweaker.api.deserializer.DeserializerBase;
import com.teamacronymcoders.contenttweaker.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockContent;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VanillaDeserializers {
    private VanillaJSONModule module;
    public VanillaDeserializers(VanillaJSONModule module) {
        this.module = module;
    }

    @SubscribeEvent
    public void registerDeserializers(RegisterDeserializerEvent event) {
        event.register(new DeserializerBase<>("Block", BlockRepresentation.class, blockRepresentation -> {
            this.module.getBlockRegistry().register(new BlockContent(blockRepresentation));
        }));
    }
}

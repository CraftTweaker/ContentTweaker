package com.teamacronymcoders.tailoredobjects.modules.vanilla;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerBase;
import com.teamacronymcoders.tailoredobjects.api.deserializer.RegisterDeserializerEvent;
import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemStackRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityRepresentation;

public class VanillaDeserializers {
    public void registerDeserializers(RegisterDeserializerEvent event) {
        event.register(new DeserializerBase<>("Block", BlockRepresentation.class, blockRepresentation -> true));
        event.register(new DeserializerBase<>("Item", ItemRepresentation.class, itemRepresentation -> true));
        event.register(new DeserializerBase<>("TileEntity", TileEntityRepresentation.class, tileEntityRepresentation -> true));
        event.register(new DeserializerBase<>("ItemStack", ItemStackRepresentation.class, itemStackRepresentation -> true));

        event.register(new DeserializerBase<>("BlockCommand", BlockCommandRepresentation.class, blockCommandRepresentation -> true));
        event.register(new DeserializerBase<>("ItemCommand", ItemCommandRepresentation.class, itemCommandRepresentation -> true));
        event.register(new DeserializerBase<>("TileEntityCommand", TileEntityRepresentation.class, tileEntityRepresentation -> true));
    }
}

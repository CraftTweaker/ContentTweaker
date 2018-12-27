package com.teamacronymcoders.contenttweaker.api.ctobjects.capabilities.item;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import com.teamacronymcoders.contenttweaker.api.ctobjects.tileentity.IMCTileEntity;
import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.items.CapabilityItemHandler;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Optional;

public class TileEntityItemCapExpansion {
    @ZenMethod
    public static ItemCapability getItemHandler(IMCTileEntity tileEntity, Facing facing) {
        return Optional.ofNullable(tileEntity.getInternal().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getInternal()))
                .map(ItemCapability::new)
                .orElse(null);
    }
}

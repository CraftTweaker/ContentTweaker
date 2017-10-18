package com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Hand;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.MCMutableItemStack;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class CTPlayer extends MCPlayer implements ICTPlayer {
    private EntityPlayer player;

    public CTPlayer(EntityPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public IMutableItemStack getHeldItem(Hand hand) {
        return new MCMutableItemStack(this.player.getHeldItem(hand.getInternal()));
    }
}

package com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity;

import com.google.common.collect.Maps;
import com.teamacronymcoders.base.tileentities.TileEntityBase;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.MCBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.MCWorld;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class TileEntityContent extends TileEntityBase implements ITickable {
    public static final Map<String, TileEntityRepresentation> REPRESENTATION_MAP = Maps.newHashMap();

    private TileEntityRepresentation representation;
    private IData tileEntityData;
    private MCWorld zenWorld;
    private MCBlockPos zenPos;

    @SuppressWarnings("unused")
    public TileEntityContent() {
        this(new TileEntityRepresentation("none"));
    }

    public TileEntityContent(TileEntityRepresentation representation) {
        this.representation = representation;
        this.tileEntityData = new DataMap(Maps.newHashMap(), false);
    }

    @Override
    protected void readFromDisk(NBTTagCompound data) {
        String representationName = data.getString("representation");
        representation = Optional.ofNullable(REPRESENTATION_MAP.get(representationName))
                .orElseGet(() -> new TileEntityRepresentation(representationName));
        tileEntityData = NBTConverter.from(data.getCompoundTag("data"), false);
    }

    @Override
    protected NBTTagCompound writeToDisk(NBTTagCompound data) {
        data.setString("representation", representation.name);
        data.setTag("data", NBTConverter.from(tileEntityData));
        return data;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            representation.clientUpdate.onUpdate(zenWorld, zenPos, tileEntityData);
        } else {
            representation.serverUpdate.onUpdate(zenWorld, zenPos, tileEntityData);
        }
    }

    @Override
    public void setWorld(@Nonnull World world) {
        super.setWorld(world);
        this.zenWorld = new MCWorld(world);
    }

    @Override
    public void setPos(BlockPos blockPos) {
        super.setPos(blockPos);
        this.zenPos = new MCBlockPos(blockPos);
    }
}

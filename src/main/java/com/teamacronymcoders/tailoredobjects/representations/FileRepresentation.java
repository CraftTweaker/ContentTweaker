package com.teamacronymcoders.tailoredobjects.representations;

import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemStackRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityRepresentation;

import java.util.List;

public class FileRepresentation {
    private List<BlockRepresentation> blocks;
    private List<ItemRepresentation> items;
    private List<TileEntityRepresentation> tileEntities;
    private List<BlockCommandRepresentation> blockCommands;
    private List<ItemCommandRepresentation> itemCommands;
    private List<TileEntityCommandRepresentation> tileEntityCommands;
    private List<ItemStackRepresentation> itemStacks;

    public List<BlockRepresentation> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockRepresentation> blocks) {
        this.blocks = blocks;
    }

    public List<ItemRepresentation> getItems() {
        return items;
    }

    public void setItems(List<ItemRepresentation> items) {
        this.items = items;
    }

    public List<TileEntityRepresentation> getTileEntities() {
        return tileEntities;
    }

    public void setTileEntities(List<TileEntityRepresentation> tileEntities) {
        this.tileEntities = tileEntities;
    }

    public List<BlockCommandRepresentation> getBlockCommands() {
        return blockCommands;
    }

    public void setBlockCommands(List<BlockCommandRepresentation> blockCommands) {
        this.blockCommands = blockCommands;
    }

    public List<ItemCommandRepresentation> getItemCommands() {
        return itemCommands;
    }

    public void setItemCommands(List<ItemCommandRepresentation> itemCommands) {
        this.itemCommands = itemCommands;
    }

    public List<TileEntityCommandRepresentation> getTileEntityCommands() {
        return tileEntityCommands;
    }

    public void setTileEntityCommands(List<TileEntityCommandRepresentation> tileEntityCommands) {
        this.tileEntityCommands = tileEntityCommands;
    }

    public List<ItemStackRepresentation> getItemStacks() {
        return itemStacks;
    }

    public void setItemStacks(List<ItemStackRepresentation> itemStacks) {
        this.itemStacks = itemStacks;
    }
}

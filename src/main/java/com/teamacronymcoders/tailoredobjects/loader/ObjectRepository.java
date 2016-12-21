package com.teamacronymcoders.tailoredobjects.loader;

import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.items.ItemStackRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityCommandRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityRepresentation;

import java.util.HashMap;
import java.util.Map;

public class ObjectRepository {
    private Map<String, BlockRepresentation> blocks;
    private Map<String, ItemRepresentation> items;
    private Map<String, TileEntityRepresentation> tiles;
    private Map<String, BlockCommandRepresentation> blockCommands;
    private Map<String, ItemCommandRepresentation> itemCommands;
    private Map<String, TileEntityCommandRepresentation> tileCommands;
    private Map<String, ItemStackRepresentation> itemStacks;

    public ObjectRepository() {
        blocks = new HashMap<>();
        items = new HashMap<>();
        tiles = new HashMap<>();
        blockCommands = new HashMap<>();
        itemCommands = new HashMap<>();
        tileCommands = new HashMap<>();
        itemStacks = new HashMap<>();
    }

    public void addBlock(BlockRepresentation blockRepresentation) {
        blocks.put(blockRepresentation.getUnlocalizedName(), blockRepresentation);
    }

    public void addItem(ItemRepresentation itemRepresentation) {
        items.put(itemRepresentation.getUnlocalizedName(), itemRepresentation);
    }

    public void addTile(TileEntityRepresentation tileEntityRepresentation) {
        tiles.put(tileEntityRepresentation.getName(), tileEntityRepresentation);
    }

    public void addBlockCommand(BlockCommandRepresentation blockRepresentation) {
        blockCommands.put(blockRepresentation.getName(), blockRepresentation);
    }

    public void addItemCommand(ItemCommandRepresentation itemRepresentation) {
        itemCommands.put(itemRepresentation.getName(), itemRepresentation);
    }

    public void addTileCommand(TileEntityCommandRepresentation tileEntityRepresentation) {
        tileCommands.put(tileEntityRepresentation.getName(), tileEntityRepresentation);
    }

    public void addItemStack(ItemStackRepresentation itemStackRepresentation) {
        itemStacks.put(itemStackRepresentation.getName(), itemStackRepresentation);
    }

    public Map<String, BlockRepresentation> getBlocks() {
        return blocks;
    }

    public Map<String, ItemRepresentation> getItems() {
        return items;
    }

    public Map<String, TileEntityRepresentation> getTiles() {
        return tiles;
    }

    public Map<String, BlockCommandRepresentation> getBlockCommands() {
        return blockCommands;
    }

    public Map<String, ItemCommandRepresentation> getItemCommands() {
        return itemCommands;
    }

    public Map<String, TileEntityCommandRepresentation> getTileCommands() {
        return tileCommands;
    }

    public Map<String, ItemStackRepresentation> getItemStacks() {
        return itemStacks;
    }
}

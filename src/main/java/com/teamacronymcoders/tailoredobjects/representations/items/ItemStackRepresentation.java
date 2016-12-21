package com.teamacronymcoders.tailoredobjects.representations.items;

public class ItemStackRepresentation {
    private String name;
    private String itemRegistryName;
    private int amount;
    private int metadata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemRegistryName() {
        return itemRegistryName;
    }

    public void setItemRegistryName(String itemRegistryName) {
        this.itemRegistryName = itemRegistryName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMetadata() {
        return metadata;
    }

    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }
}

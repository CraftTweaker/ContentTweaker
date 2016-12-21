package com.teamacronymcoders.tailoredobjects.representations.items;

import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;

import java.util.List;
import java.util.Map;

public class ItemRepresentation {
    private String localizedName;
    private String unlocalizedName;
    private String textureName;
    private int maxStackSize;
    private EnumAction itemUseAction;
    private int maxItemUseDuration;
    private List<String> information;
    private boolean hasEffect;
    private EnumRarity rarity;
    private String creativeTab;
    private int entityLifespan;
    private float getSmeltingExperience;
    private boolean isBeaconPayment;
    private Map<String, Integer> toolClasses;
    private String commandName;
    private ItemCommandRepresentation commands;

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public EnumAction getItemUseAction() {
        return itemUseAction;
    }

    public void setItemUseAction(EnumAction itemUseAction) {
        this.itemUseAction = itemUseAction;
    }

    public int getMaxItemUseDuration() {
        return maxItemUseDuration;
    }

    public void setMaxItemUseDuration(int maxItemUseDuration) {
        this.maxItemUseDuration = maxItemUseDuration;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    public boolean isHasEffect() {
        return hasEffect;
    }

    public void setHasEffect(boolean hasEffect) {
        this.hasEffect = hasEffect;
    }

    public EnumRarity getRarity() {
        return rarity;
    }

    public void setRarity(EnumRarity rarity) {
        this.rarity = rarity;
    }

    public String getCreativeTab() {
        return creativeTab;
    }

    public void setCreativeTab(String creativeTab) {
        this.creativeTab = creativeTab;
    }

    public int getEntityLifespan() {
        return entityLifespan;
    }

    public void setEntityLifespan(int entityLifespan) {
        this.entityLifespan = entityLifespan;
    }

    public float getGetSmeltingExperience() {
        return getSmeltingExperience;
    }

    public void setGetSmeltingExperience(float getSmeltingExperience) {
        this.getSmeltingExperience = getSmeltingExperience;
    }

    public boolean isBeaconPayment() {
        return isBeaconPayment;
    }

    public void setBeaconPayment(boolean beaconPayment) {
        isBeaconPayment = beaconPayment;
    }

    public Map<String, Integer> getToolClasses() {
        return toolClasses;
    }

    public void setToolClasses(Map<String, Integer> toolClasses) {
        this.toolClasses = toolClasses;
    }

    public ItemCommandRepresentation getCommands() {
        return commands;
    }

    public void setCommands(ItemCommandRepresentation commands) {
        this.commands = commands;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}

package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.types.basic.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.food.MCFood;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;

/**
 * The item builder is to... build items (surprise!)
 * <p>
 * It allows you to set various properties that will change how the item behaves and what it can do.
 * You can also use {@link #withType} to switch to a more specialized builder, if there exist any.
 * <p>
 * To tell CoT that you want the item to appear in-game you need to call {@link #build(String)} and specify a valid resource location path.
 *
 * @docParam this new ItemBuilder()
 */
@ZenRegister
@Document("mods/contenttweaker/API/item/ItemBuilder")
@ZenCodeType.Name("mods.contenttweaker.item.ItemBuilder")
public class ItemBuilder implements IIsBuilder {
    
    private final Item.Properties itemProperties;
    public boolean allowTinted;
    
    /**
     * Creates a new ItemBuilder.
     * Remember that this will _not_ create a new block in the game, you need to call {@link #build(String)} for that.
     */
    @ZenCodeType.Constructor
    public ItemBuilder() {
        itemProperties = new Item.Properties().group(ItemGroup.MISC);
    }
    
    public Item.Properties getItemProperties() {
        return itemProperties;
    }
    
    /**
     * Allows you to set the maximum stack size for this item.<br/>
     * Be warned that this cannot be used in combination with {@link #withMaxDamage}!
     *
     * @param maxStackSize The maximum stack size
     * @return This builder, used for method chaining
     * @docParam maxStackSize 16
     */
    @ZenCodeType.Method
    public ItemBuilder withMaxStackSize(int maxStackSize) {
        itemProperties.maxStackSize(maxStackSize);
        return this;
    }
    
    /**
     * Allows you to set the maximum damage for this item.<br/>
     * Be warned that this cannot be used in combination with {@link #withMaxStackSize}!
     *
     * @param maxDamage The maximum stack size
     * @return This builder, used for method chaining
     * @docParam maxDamage 250
     */
    @ZenCodeType.Method
    public ItemBuilder withMaxDamage(int maxDamage) {
        itemProperties.maxDamage(maxDamage);
        return this;
    }
    
    /**
     * Allows you to set the item group that this item will appear in.
     * By default, items will land in `misc`
     *
     * @param itemGroup The item group this item should appear in
     * @return This builder, used for method chaining
     * @docParam itemGroup <itemGroup:misc>
     */
    @ZenCodeType.Method
    public ItemBuilder withItemGroup(MCItemGroup itemGroup) {
        itemProperties.group(itemGroup.getInternal());
        return this;
    }
    
    /**
     * Allows you to set the item's rarity
     *
     * @param rarity The rarity
     * @return This builder, used for method chaining
     * @docParam rarity "EPIC"
     */
    @ZenCodeType.Method
    public ItemBuilder withRarity(String rarity) {
        itemProperties.rarity(Rarity.valueOf(rarity));
        return this;
    }
    
    /**
     * Sets that this item may not be repaired in an anvil
     *
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public ItemBuilder withNoRepair() {
        itemProperties.setNoRepair();
        return this;
    }

    /**
     * Sets that this item is a food
     *
     * @param food the food
     * @return The builder, used for method chaining
     */
    @ZenCodeType.Method
    public ItemBuilder withFood(MCFood food) {
        itemProperties.food(food.getInternal());
        return this;
    }

    /**
     * Sets that this item is immune to fire
     *
     * @return The builder, used for method chaining
     */
    @ZenCodeType.Method
    public ItemBuilder isImmuneToFire() {
        itemProperties.isImmuneToFire();
        return this;
    }

    /**
     * Sets that this item can be tinted.
     * @return The builder, used for method chaining
     */
    @ZenCodeType.Method
    public ItemBuilder allowTinted() {
        this.allowTinted = true;
        return this;
    }

    /**
     * Sets the specific type of this item.
     * After this method is called the builder's context will switch to the more provided type builder.
     * That means that the methods of this builder will no longer be available, so any properties you wish to set should be set before you call this method.
     *
     * @param typeOfT Internally used by ZC to handle the generic param
     * @param <T>     The Type of item that this should become
     * @return A builder with the given item.
     * @docParam T mods.contenttweaker.item.tool.ItemBuilderTool
     */
    @ZenCodeType.Method
    public <T extends ItemTypeBuilder> T withType(Class<T> typeOfT) {
        try {
            final Constructor<T> constructor = typeOfT.getConstructor(ItemBuilder.class);
            return constructor.newInstance(this);
        } catch(InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            CraftTweakerAPI.logThrowing("Could not instantiate Specialized Item Builder!", e);
        }
        return null;
    }
    
    @Override
    public void build(ResourceLocation location) {
        withType(ItemBuilderBasic.class).build(location);
    }
}

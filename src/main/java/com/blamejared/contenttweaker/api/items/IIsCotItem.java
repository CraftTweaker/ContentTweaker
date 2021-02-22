package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.extensions.IForgeItem;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Item. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 *
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 */
@ZenRegister
@Document("mods/contenttweaker/API/item/IIsCotItem")
@ZenCodeType.Name("mods.contenttweaker.item.IIsCotItem")
public interface IIsCotItem extends IHasResourceLocation, IHasResourcesToWrite, IForgeItem {
    /**
     * Sets what will happen when the player uses on a block with the item
     *
     * @param func an IItemUse function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnItemUse(IItemUse func);

    /**
     * Sets what will happen when the player right clicks with the item. If the item is food, you can't use it.
     * Because food handling also needs it.
     *
     * @param func an IItemRightClick function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnItemRightClick(IItemRightClick func);

    /**
     * Sets what will happen when a living entity attacks other entities.
     *
     * @param func an IItemHitEntity function, the function return whether can attack or not.
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnHitEntity(IItemHitEntity func);

    /**
     * Sets what will happen when a player interacts (right-clicks) a entity.
     *
     * @param func an IItemInteractWithEntity function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     *
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnInteractWithEntity(IItemInteractWithEntity func);

    /**
     * The Set function will be called each tick as long the item is on a player inventory.
     * @param func an IItemInventoryTick function
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setInventoryTick(IItemInventoryTick func);
}

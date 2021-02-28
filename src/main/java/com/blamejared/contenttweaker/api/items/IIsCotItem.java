package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.actions.ActionSetFunction;
import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraftforge.common.extensions.IForgeItem;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Item. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 *
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via cotItem BEP.
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
    default IIsCotItem setOnItemUse(IItemUse func) {
        if (this.getItem() instanceof BlockItem) {
            throw new UnsupportedOperationException("You could not set onItemRightClick to a block item!");
        }
        ActionSetFunction.applyNewAction(func, IItemUse.class, this);
        return this;
    }

    /**
     * Sets what will happen when the player right clicks with the item. If the item is food, you can't use it.
     * Because food handling also needs it.
     *
     * @param func an IItemRightClick function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setOnItemRightClick(IItemRightClick func) {
        if (this.getItem().isFood()) {
            throw new UnsupportedOperationException("You could not set onItemRightClick to a food item!");
        }
        ActionSetFunction.applyNewAction(func, IItemRightClick.class, this);
        return this;
    }

    /**
     * Sets what will happen when a living entity attacks other entities.
     *
     * @param func an IItemHitEntity function, the function return whether can attack or not.
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setOnHitEntity(IItemHitEntity func) {
        ActionSetFunction.applyNewAction(func, IItemHitEntity.class, this);
        return this;
    }

    /**
     * Sets what will happen when a player interacts (right-clicks) a entity.
     *
     * @param func an IItemInteractWithEntity function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     *
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setOnInteractWithEntity(IItemInteractWithEntity func) {
        ActionSetFunction.applyNewAction(func, IItemInteractWithEntity.class, this);
        return this;
    }

    /**
     * The Set function will be called each tick as long the item is on a player inventory.
     * @param func an IItemInventoryTick function
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setInventoryTick(IItemInventoryTick func) {
        ActionSetFunction.applyNewAction(func, IItemInventoryTick.class, this);
        return this;
    }

    /**
     * The Set function will be called each tick while using the item
     * @param func an IItemUsingTick function. The count argument of function is the amount of time in tick the item has been used for continuously.
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setUsingTick(IItemUsingTick func) {
        if (this.getItem() instanceof BlockItem) {
            throw new UnsupportedOperationException("You could not set onUsingTick to a block item!");
        }
        ActionSetFunction.applyNewAction(func, IItemUsingTick.class, this);
        return this;
    }

    /**
     * The item's color
     * @param func an IItemColorSupplier, The tintIndex is `layerX` property of its model.
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    default IIsCotItem setItemColorSupplier(IItemColorSupplier func) {
        ActionSetFunction.applyNewAction(func, IItemColorSupplier.class, this);
        return this;
    }
}

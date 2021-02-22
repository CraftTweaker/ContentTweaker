package com.blamejared.contenttweaker.api.items;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.contenttweaker.api.functions.IItemRightClick;
import com.blamejared.contenttweaker.api.functions.IItemUse;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.extensions.IForgeItem;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Item. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 */
@ZenRegister
@Document("mods/contenttweaker/API/item/IIsCotItem")
@ZenCodeType.Name("mods.contenttweaker.item.IIsCotItem")
public interface IIsCotItem extends IHasResourceLocation, IHasResourcesToWrite, IForgeItem {
    /**
     * Sets what will happen when the player use on a block with the item
     *
     * @param func an IItemUse function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnItemUse(IItemUse func);

    /**
     * Sets what will happen when the player right click with the item. If the item is food, you can't use it.
     * Because food handling also needs it.
     *
     * @param func an IItemRightClick function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCotItem, used for method chaining
     */
    @ZenCodeType.Method
    IIsCotItem setOnItemRightClick(IItemRightClick func);
}

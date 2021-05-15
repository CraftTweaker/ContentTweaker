package com.blamejared.contenttweaker.items.types.advance;

import com.blamejared.contenttweaker.actions.ActionSetFunction;
import com.blamejared.contenttweaker.actions.ActionSetFunctionClient;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.color.IItemHasColor;
import com.blamejared.contenttweaker.items.types.basic.CoTItemBasic;
import com.blamejared.contenttweaker.wrappers.MCItemUseContext;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

/**
 * A registered CoT Item. Used for advanced functionality. like onItemUse, onItemRightClick etc.
 *
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via advanced item BEP.
 *
 * @docParam this <advanceditem:test_item>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.advance.CoTItemAdvanced")
@Document("mods/contenttweaker/API/item/advance/CoTItemAdvanced")
public class CoTItemAdvanced extends CoTItemBasic implements IIsCotItem, IItemHasColor {
    public CoTItemAdvanced(Properties properties, ResourceLocation location) {
        super(properties, location);
    }

    private IItemUse itemUse;
    private IItemRightClick itemRightClick;
    private IItemHitEntity itemHitEntity;
    private IItemInteractWithEntity itemInteractWithEntity;
    private IItemUsingTick itemUsingTick;
    private IItemColorSupplier itemColorSupplier = IItemColorSupplier.DEFAULT;
    private IItemInventoryTick itemInventoryTick;

    /**
     * Sets what will happen when the player uses on a block with the item
     *
     * @param func an IItemUse function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setOnItemUse(IItemUse func) {
        ActionSetFunction.applyNewAction("onItemUse", this, func, (item, fun) -> item.itemUse = fun);
        return this;
    }

    /**
     * Sets what will happen when the player right clicks with the item. If the item is food, you can't use it.
     * Because food handling also needs it.
     *
     * @param func an IItemRightClick function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setOnItemRightClick(IItemRightClick func) {
        if (this.getItem().isFood()) {
            throw new UnsupportedOperationException("You could not set onItemRightClick to a food item!");
        }
        ActionSetFunction.applyNewAction("onItemRightClick", this, func, (item, fun) -> item.itemRightClick = fun);
        return this;
    }

    /**
     * Sets what will happen when a living entity attacks other entities.
     *
     * @param func an IItemHitEntity function, the function return whether can attack or not.
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setOnHitEntity(IItemHitEntity func) {
        ActionSetFunction.applyNewAction("onHitEntity", this, func, (item, fun) -> item.itemHitEntity = itemHitEntity);
        return this;
    }

    /**
     * Sets what will happen when a player interacts (right-clicks) a entity.
     *
     * @param func an IItemInteractWithEntity function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     *
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setOnInteractWithEntity(IItemInteractWithEntity func) {
        ActionSetFunction.applyNewAction("onInteractWithEntity", this, func, (item, fun) -> item.itemInteractWithEntity = func);
        return this;
    }

    /**
     * The Set function will be called each tick as long the item is on a player inventory.
     * @param func an IItemInventoryTick function
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setInventoryTick(IItemInventoryTick func) {
        ActionSetFunction.applyNewAction("inventoryTick", this, func, (item, fun) -> item.itemInventoryTick = fun);
        return this;
    }

    /**
     * The Set function will be called each tick while using the item
     * @param func an IItemUsingTick function. The count argument of function is the amount of time in tick the item has been used for continuously.
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setUsingTick(IItemUsingTick func) {
        ActionSetFunction.applyNewAction("usingTick", this, func, (item, fun) -> item.itemUsingTick = itemUsingTick);
        return this;
    }

    /**
     * The item's color. Before using it, you should set the item can be tinted first via adding `allowTinted` to linked item builder.
     * @param func an IItemColorSupplier, The tintIndex is `layerX` property of its model.
     * @return the CoTItemAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTItemAdvanced setItemColorSupplier(IItemColorSupplier func) {
        ActionSetFunctionClient.applyNewAction("itemColorSupplier", this, func, IItemColorSupplier.DEFAULT, (item, fun) -> item.itemColorSupplier = itemColorSupplier);
        return this;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (itemUse != null) {
            return itemUse.apply(new MCItemUseContext(context));
        } else {
            return super.onItemUse(context);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return Optional.ofNullable(itemRightClick)
                .map(iItemRightClick -> {
                    ItemStack stack = playerIn.getHeldItem(handIn);
                    return new ActionResult<>(iItemRightClick.apply(new MCItemStackMutable(stack), playerIn, worldIn, handIn), stack);
                }).orElseGet(() -> super.onItemRightClick(worldIn, playerIn, handIn));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (itemHitEntity != null) {
            return itemHitEntity.apply(new MCItemStackMutable(stack), target, attacker);
        } else {
            return super.hitEntity(stack, target, attacker);
        }
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (itemInteractWithEntity != null) {
            return itemInteractWithEntity.apply(new MCItemStackMutable(stack), playerIn, target, hand);
        } else {
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (itemInventoryTick != null) {
            itemInventoryTick.apply(new MCItemStackMutable(stack), worldIn, entityIn, itemSlot, isSelected);
        } else {
            super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (itemUsingTick != null) {
            itemUsingTick.apply(new MCItemStackMutable(stack), player, count);
        } else {
            super.onUsingTick(stack, player, count);
        }
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return itemColorSupplier.apply(new MCItemStack(stack), tintIndex);
    }

}

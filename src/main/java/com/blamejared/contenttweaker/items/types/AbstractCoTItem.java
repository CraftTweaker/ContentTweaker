package com.blamejared.contenttweaker.items.types;

import com.blamejared.contenttweaker.actions.*;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.wrappers.MCItemUseContext;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public abstract class AbstractCoTItem extends Item implements IIsCotItem {
    public AbstractCoTItem(Properties properties) {
        super(properties);
    }

    public IItemUse itemUse;
    public IItemRightClick itemRightClick;
    public IItemHitEntity itemHitEntity;
    public IItemInteractWithEntity itemInteractWithEntity;
    public IItemInventoryTick itemInventoryTick;
    public IItemUsingTick itemUsingTick;

    @Override
    public IIsCotItem setOnItemUse(IItemUse func) {
        CraftTweakerAPI.apply(new ActionSetItemOnItemUse(func, this));
        return this;
    }

    @Override
    public IIsCotItem setOnItemRightClick(IItemRightClick func) {
        if (this.getItem().isFood()) {
            throw new UnsupportedOperationException("could not set onItemRightClick to food item");
        }
        CraftTweakerAPI.apply(new ActionSetItemOnItemRightClick(func, this));
        return this;
    }

    @Override
    public IIsCotItem setOnHitEntity(IItemHitEntity func) {
        CraftTweakerAPI.apply(new ActionSetItemOnHitEntity(func, this));
        return this;
    }

    @Override
    public IIsCotItem setOnInteractWithEntity(IItemInteractWithEntity func) {
        CraftTweakerAPI.apply(new ActionSetItemOnInteractWithEntity(func, this));
        return this;
    }

    @Override
    public IIsCotItem setInventoryTick(IItemInventoryTick func) {
        CraftTweakerAPI.apply(new ActionSetItemInventoryTick(func, this));
        return this;
    }

    @Override
    public IIsCotItem setUsingTick(IItemUsingTick func) {
        CraftTweakerAPI.apply(new ActionSetItemUsingTick(func, this));
        return this;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (itemUse == null) {
            return super.onItemUse(context);
        } else {
            return ActionResultType.valueOf(itemUse.apply(new MCItemUseContext(context)));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (itemRightClick == null) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        } else {
            ItemStack stack = playerIn.getHeldItem(handIn);
            switch (itemRightClick.apply(new MCItemStackMutable(stack), playerIn, worldIn, handIn.name())) {
                case "SUCCESS":
                    return ActionResult.resultSuccess(stack);
                case "PASS":
                    return ActionResult.resultPass(stack);
                case "FAIL":
                    return ActionResult.resultFail(stack);
                case "CONSUME":
                    return ActionResult.resultConsume(stack);
                default:
                    CraftTweakerAPI.logWarning("invalid action result type! Set PASS by default.");
                    return ActionResult.resultPass(stack);
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (itemHitEntity == null) {
            return super.hitEntity(stack, target, attacker);
        } else {
            return itemHitEntity.apply(new MCItemStackMutable(stack), target, attacker);
        }
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (itemInteractWithEntity == null) {
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
        } else {
            return ActionResultType.valueOf(itemInteractWithEntity.apply(new MCItemStackMutable(stack), playerIn, target, hand.name()));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (itemInventoryTick == null) {
            super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        } else {
            itemInventoryTick.apply(new MCItemStackMutable(stack), worldIn, entityIn, itemSlot, isSelected);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (itemUsingTick == null) {
            super.onUsingTick(stack, player, count);
        } else {
            itemUsingTick.apply(new MCItemStackMutable(stack), player, count);
        }
    }
}

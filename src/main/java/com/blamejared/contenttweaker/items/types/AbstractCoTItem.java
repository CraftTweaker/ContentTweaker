package com.blamejared.contenttweaker.items.types;

import com.blamejared.contenttweaker.actions.ActionSetItemOnItemRightClick;
import com.blamejared.contenttweaker.actions.ActionSetItemOnItemUse;
import com.blamejared.contenttweaker.api.functions.IItemRightClick;
import com.blamejared.contenttweaker.api.functions.IItemUse;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.wrappers.MCItemUseContext;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
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
}

package com.blamejared.contenttweaker.items.types;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.wrappers.MCItemUseContext;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.item.*;
import mcp.*;
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

import javax.annotation.*;


/**
 * @author youyihj
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractCoTItem extends Item implements IIsCotItem {
    public AbstractCoTItem(Properties properties) {
        super(properties);
    }
    
    private boolean allowTinted;
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemUse.class)
                .map(iItemUse -> ActionResultType.valueOf(iItemUse.apply(new MCItemUseContext(context))))
                .orElseGet(() -> super.onItemUse(context));
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemRightClick.class)
                .map(iItemRightClick -> {
                    ItemStack stack = playerIn.getHeldItem(handIn);
                    switch (iItemRightClick.apply(new MCItemStack(stack), playerIn, worldIn, handIn)) {
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
                }).orElseGet(() -> super.onItemRightClick(worldIn, playerIn, handIn));
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemHitEntity.class)
                .map(iItemHitEntity -> iItemHitEntity.apply(new MCItemStack(stack), target, attacker))
                .orElseGet(() -> super.hitEntity(stack, target, attacker));
    }
    
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemInteractWithEntity.class)
                .map(iItemInteractWithEntity -> ActionResultType.valueOf(iItemInteractWithEntity.apply(new MCItemStack(stack), playerIn, target, hand)))
                .orElseGet(() -> super.itemInteractionForEntity(stack, playerIn, target, hand));
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        VanillaFactory.REGISTRY.getFunction(this, IItemInventoryTick.class)
                .map(iItemInventoryTick -> {
                    iItemInventoryTick.apply(new MCItemStack(stack), worldIn, entityIn, itemSlot, isSelected);
                    return 0;
                })
                .orElseGet(() -> {
                    super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
                    return 0;
                });
    }
    
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        VanillaFactory.REGISTRY.getFunction(this, IItemUsingTick.class)
                .map(iItemUsingTick -> {
                    iItemUsingTick.apply(new MCItemStack(stack), player, count);
                    return 0;
                })
                .orElseGet(() -> {
                    super.onUsingTick(stack, player, count);
                    return 0;
                });
    }
    
    @Override
    public boolean allowTinted() {
        return allowTinted;
    }
    
    @Override
    public void setAllowTinted() {
        allowTinted = true;
    }
}

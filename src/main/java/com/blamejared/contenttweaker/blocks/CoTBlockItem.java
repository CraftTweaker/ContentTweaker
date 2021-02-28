package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.*;
import java.util.*;

public class CoTBlockItem extends BlockItem implements IIsCotItem {
    
    public CoTBlockItem(IIsCoTBlock blockIn, Item.Properties builder) {
        super(blockIn.getBlock(), builder);
        this.setRegistryName(blockIn.getRegistryNameNonNull());
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final ResourceLocation location = getRegistryNameNonNull();
        final List<WriteableResource> out = new ArrayList<>();
        out.add(WriteableResourceImage.noImage(ImageType.ITEM, location));
        final WriteableResourceTemplate modelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "item").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_block")).setLocationProperty(location);
        out.add(modelTemplate);
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        VanillaFactory.REGISTRY.getFunction(this, IItemInventoryTick.class)
                .map(iItemInventoryTick -> {
                    iItemInventoryTick.apply(new MCItemStackMutable(stack), worldIn, entityIn, itemSlot, isSelected);
                    return 0;
                })
                .orElseGet(() -> {
                    super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
                    return 0;
                });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemRightClick.class)
                .map(iItemRightClick -> {
                    ItemStack stack = playerIn.getHeldItem(handIn);
                    switch (iItemRightClick.apply(new MCItemStackMutable(stack), playerIn, worldIn, handIn.name())) {
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
                .map(iItemHitEntity -> iItemHitEntity.apply(new MCItemStackMutable(stack), target, attacker))
                .orElseGet(() -> super.hitEntity(stack, target, attacker));
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return VanillaFactory.REGISTRY.getFunction(this, IItemInteractWithEntity.class)
                .map(iItemInteractWithEntity -> ActionResultType.valueOf(iItemInteractWithEntity.apply(new MCItemStackMutable(stack), playerIn, target, hand.name())))
                .orElseGet(() -> super.itemInteractionForEntity(stack, playerIn, target, hand));
    }
}

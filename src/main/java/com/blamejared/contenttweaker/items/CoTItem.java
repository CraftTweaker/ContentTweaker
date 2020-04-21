package com.blamejared.contenttweaker.items;

import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;

public class CoTItem extends Item {
    
    private final MCItemRepresentation representation;
    
    public CoTItem(MCItemRepresentation representation) {
        super(representation.getProperties().getInternal());
        this.representation = representation;
    }
    
    @Override
    public void func_219972_a(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        super.func_219972_a(worldIn, livingEntityIn, stack, count);
    }
    
    @Nullable
    @Override
    public IItemPropertyGetter getPropertyGetter(ResourceLocation key) {
        return super.getPropertyGetter(key);
    }
    
    @Override
    public boolean hasCustomProperties() {
        return super.hasCustomProperties();
    }
    
    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        return super.updateItemStackNBT(nbt);
    }
    
    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return super.canPlayerBreakBlockWhileHolding(state, worldIn, pos, player);
    }
    
    @Override
    public Item asItem() {
        return super.asItem();
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
    
    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.hitEntity(stack, target, attacker);
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
    
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return super.canHarvestBlock(blockIn);
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
    
    @Override
    public ITextComponent getName() {
        return super.getName();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    protected String getDefaultTranslationKey() {
        return super.getDefaultTranslationKey();
    }
    
    @Override
    public String getTranslationKey() {
        return super.getTranslationKey();
    }
    
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack);
    }
    
    @Override
    public boolean shouldSyncTag() {
        return super.shouldSyncTag();
    }
    
    @Override
    public boolean hasContainerItem() {
        return super.hasContainerItem();
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }
    
    @Override
    public boolean isComplex() {
        return super.isComplex();
    }
    
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return super.getUseAction(stack);
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return super.getUseDuration(stack);
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }
    
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return super.getDisplayName(stack);
    }
    
    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack);
    }
    
    @Override
    public Rarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return super.isEnchantable(stack);
    }
    
    @Override
    public int getItemEnchantability() {
        return super.getItemEnchantability();
    }
    
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
    }
    
    @Override
    protected boolean isInGroup(ItemGroup group) {
        return super.isInGroup(group);
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return super.getAttributeModifiers(equipmentSlot);
    }
    
    @Override
    public boolean isRepairable(ItemStack stack) {
        return super.isRepairable(stack);
    }
    
    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return super.getToolTypes(stack);
    }
    
    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return super.getHarvestLevel(stack, tool, player, blockState);
    }
    
    @Override
    public Set<ResourceLocation> getTags() {
        return super.getTags();
    }
    
    @Override
    public boolean isCrossbow(ItemStack stack) {
        return super.isCrossbow(stack);
    }
    
    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }
    
    @Override
    public boolean isIn(Tag<Item> tagIn) {
        return super.isIn(tagIn);
    }
    
    @Override
    public boolean isFood() {
        return super.isFood();
    }
    
    @Nullable
    @Override
    public Food getFood() {
        return super.getFood();
    }
}

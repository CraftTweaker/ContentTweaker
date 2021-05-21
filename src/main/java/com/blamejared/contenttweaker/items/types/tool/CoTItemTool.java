package com.blamejared.contenttweaker.items.types.tool;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public final class CoTItemTool extends Item implements IIsCotItem {
    
    private final Map<ToolType, Float> miningSpeeds;
    private final double attackDamage;
    private final double attackSpeed;
    private final int durabilityCostAttack;
    private final int durabilityCostMining;
    
    public CoTItemTool(ItemBuilderTool builder, ResourceLocation location) {
        super(builder.getItemBuilder().getItemProperties());
        this.setRegistryName(location);
        miningSpeeds = builder.getMiningSpeeds();
        attackSpeed = builder.getAttackSpeed();
        attackDamage = builder.getAttackDamage();
        durabilityCostAttack = builder.getDurabilityCostAttack();
        durabilityCostMining = builder.getDurabilityCostMining();
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        final ToolType harvestTool = state.getHarvestTool();
        if(miningSpeeds.containsKey(harvestTool)) {
            return miningSpeeds.get(harvestTool);
        }
        return super.getDestroySpeed(stack, state);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if(slot == EquipmentSlotType.MAINHAND) {
            if(attackDamage != 0.0D) {
                multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
            }
            if(attackSpeed != 0.0D) {
                multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
            }
        }
        
        return multimap;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(durabilityCostMining, entityLiving, holder -> holder.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
        return true;
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(durabilityCostAttack, attacker, holder -> holder.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final ResourceLocation location = getRegistryNameNonNull();
        final List<WriteableResource> out = new ArrayList<>();
        out.add(WriteableResourceImage.noImage(ImageType.ITEM, location));
        
        final WriteableResourceTemplate modelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "item").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_tool")).setLocationProperty(location);
        out.add(modelTemplate);
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }
}

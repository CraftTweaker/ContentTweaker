package com.blamejared.contenttweaker.items.types.tool;

import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.types.basic.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;

@ParametersAreNonnullByDefault
final class CoTItemTool extends CoTItemBasic implements IIsCotItem {
    
    private final Map<ToolType, Float> miningSpeeds;
    private final double attackDamage;
    private final double attackSpeed;
    private final int durabilityCostAttack;
    private final int durabilityCostMining;
    
    public CoTItemTool(BuilderTool builder, ResourceLocation location) {
        super(builder.getItemBuilder().getItemProperties(), location);
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if(slot == EquipmentSlotType.MAINHAND) {
            if(attackDamage != 0.0D) {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
            }
            if(attackSpeed != 0.0D) {
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
            }
        }
        
        return multimap;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(durabilityCostMining, entityLiving, holder -> holder.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
        return true;
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(durabilityCostAttack, attacker, holder -> holder.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }
}

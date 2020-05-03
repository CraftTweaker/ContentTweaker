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
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;

@ParametersAreNonnullByDefault
final class CoTItemTool extends CoTItemBasic implements IIsCotItem {
    
    private final Map<ToolType, Float> miningSpeeds;
    private final double attackDamage;
    private final double attackSpeed;
    
    public CoTItemTool(BuilderTool builder, ResourceLocation location) {
        super(builder.getBuilder().getItemProperties(), location);
        miningSpeeds = builder.getMiningSpeeds();
        attackSpeed = builder.getAttackSpeed();
        attackDamage = builder.getAttackDamage();
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
}

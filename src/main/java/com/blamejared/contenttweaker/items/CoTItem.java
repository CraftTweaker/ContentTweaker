package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.items.functions.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.entity.player.*;
import com.blamejared.crafttweaker.impl.item.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoTItem extends Item {
    
    private final Map<ToolType, ToolTypeFunction> toolClasses;
    private final ToolDestroySpeedFunction speedFunction;
    
    public CoTItem(MCItemProperties properties) {
        super(properties.createInternal());
        this.toolClasses = properties.getToolClasses();
        this.speedFunction = properties.destroySpeedFunction;
    }
    
    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        
        if(!toolClasses.containsKey(tool)) {
            return -1;
        }
        final MCPlayerEntity playerEntity = player != null ? new MCPlayerEntity(player) : null;
        final MCBlockState state = blockState != null ? new MCBlockState(blockState) : null;
        return toolClasses.get(tool)
                .getHarvestLevel(new MCItemStackMutable(stack), new MCToolType(tool), playerEntity, state);
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return speedFunction.getDestroySpeed(new MCItemStackMutable(stack), new MCBlockState(state));
    }
    
    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return toolClasses.keySet();
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(getMaxDamage(stack) == 0) {
            return false;
        }
        
        if(!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(1, entityLiving, (livingEntity) -> {
                livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        
        return true;
    }
}

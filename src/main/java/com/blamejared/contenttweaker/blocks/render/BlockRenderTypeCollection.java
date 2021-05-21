package com.blamejared.contenttweaker.blocks.render;

import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class BlockRenderTypeCollection {
    private static final Map<BlockRenderType, Collection<IIsCoTBlock>> blockRenderTypeMap = Util.make(new EnumMap<>(BlockRenderType.class), (map) -> {
        for (BlockRenderType value : BlockRenderType.values()) {
            map.put(value, new ArrayList<>());
        }
    });

    @OnlyIn(Dist.CLIENT)
    public static void registerAllRenderTypeRules() {
        blockRenderTypeMap.forEach(BlockRenderType::registerToBlocks);
    }

    public static void addRenderTypeRule(IIsCoTBlock block, BlockRenderType renderType) {
        blockRenderTypeMap.get(renderType).add(block);
    }
}

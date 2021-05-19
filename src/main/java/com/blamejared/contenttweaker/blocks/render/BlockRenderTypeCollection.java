package com.blamejared.contenttweaker.blocks.render;

import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;

import java.util.HashMap;
import java.util.Map;

public class BlockRenderTypeCollection {
    private static final Map<IIsCoTBlock, BlockRenderType> blockRenderTypeMap = new HashMap<>();

    public static void registerAllRenderTypeRules() {
        blockRenderTypeMap.forEach((block, renderType) -> renderType.registerToBlock(block));
    }

    public static void addRenderTypeRule(IIsCoTBlock block, BlockRenderType renderType) {
        blockRenderTypeMap.put(block, renderType);
    }
}

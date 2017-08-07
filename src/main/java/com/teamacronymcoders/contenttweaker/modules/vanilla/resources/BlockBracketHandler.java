package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.mc1120.block.MCSpecificBlock;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInt;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;


public class BlockBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public BlockBracketHandler() {
        method = CraftTweakerAPI.getJavaMethod(BlockBracketHandler.class, "getBlock", String.class, int.class);
    }

    public static IBlock getBlock(String name, int meta) {
        IBlock iBlock = null;
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if (block != null) {
            iBlock = new MCSpecificBlock(block, meta);
        }
        return iBlock;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        if (tokens.size() > 2) {
            if ("block".equalsIgnoreCase(tokens.get(0).getValue())) {
                String blockName;
                int meta = 0;
                if (tokens.size() >= 5) {
                    blockName = tokens.get(2).getValue() + ":" + tokens.get(4).getValue();
                    if (tokens.size() > 7) {
                        meta = Integer.parseInt(tokens.get(7).getValue());
                    }
                } else {
                    blockName = tokens.get(2).getValue();
                }
                zenSymbol = new BlockReferenceSymbol(environment, blockName, meta);
            }
        }

        return zenSymbol;
    }

    private class BlockReferenceSymbol implements IZenSymbol {

        private final IEnvironmentGlobal environment;
        private final String name;
        private final int meta;

        public BlockReferenceSymbol(IEnvironmentGlobal environment, String name, int meta) {
            this.environment = environment;
            this.name = name;
            this.meta = meta;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name), new ExpressionInt(position, meta, ZenType.INT));
        }
    }
}
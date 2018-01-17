package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.MCBlockState;
import com.teamacronymcoders.contenttweaker.zen.symbol.StringIntSymbol;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.block.MCSpecificBlock;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

@BracketHandler(priority = 100)
@ZenRegister
public class ItemBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public ItemBracketHandler() {
        method = CraftTweakerAPI.getJavaMethod(this.getClass(), "getItem", String.class, int.class);
    }

    public static IItemStack getItem(String name, int meta) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        IItemStack itemStack = null;
        if (item != null) {
            itemStack = new MCItemStack(new ItemStack(item, 1, meta));
        }
        return itemStack;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        if (tokens.size() > 2) {
            if ("item".equalsIgnoreCase(tokens.get(0).getValue())) {
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
                zenSymbol = new StringIntSymbol(environment, blockName, meta, method);
            }
        }

        return zenSymbol;
    }
}

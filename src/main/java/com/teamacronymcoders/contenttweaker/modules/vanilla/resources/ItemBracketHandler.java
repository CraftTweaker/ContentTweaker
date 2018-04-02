package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import com.teamacronymcoders.contenttweaker.zen.symbol.StringIntSymbol;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;

@BracketHandler(priority = 100)
@ZenRegister
public class ItemBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public ItemBracketHandler() {
        method = CraftTweakerAPI.getJavaMethod(this.getClass(), "getItem", String.class, int.class);
    }

    @SuppressWarnings("unused")
    public static IItemStack getItem(String name, int meta) {
        return new GhostItemStack(name, meta);
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
                    if (tokens.size() >= 7) {
                        meta = Integer.parseInt(tokens.get(6).getValue());
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

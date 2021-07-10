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
                int stage = 0;
                StringBuilder blockName = new StringBuilder();
                StringBuilder metaValue = new StringBuilder();

                for(Token tok: tokens.subList(2, tokens.size())) {
                    if(stage == 0) { // mod-id construction
                        if(":".equalsIgnoreCase(tok.getValue())) {
                            blockName.append(":");
                            stage = 1;
                        } else {
                            blockName.append(tok.getValue());
                        }
                    } else if (stage == 1) { // item name construction
                        if(":".equalsIgnoreCase(tok.getValue())) {
                            stage = 2;
                        } else {
                            blockName.append(tok.getValue());
                        }
                    } else { // meta value gathering
                        metaValue.append(tok.getValue());
                    }
                }

                int meta = 0;
                if(!metaValue.toString().equalsIgnoreCase("")) {
                    meta = Integer.parseInt(metaValue.toString());
                }
                zenSymbol = new StringIntSymbol(environment, blockName.toString(), meta, method);
            }
        }


        return zenSymbol;
    }
}

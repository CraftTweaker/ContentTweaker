package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.brackets.BracketHandlerLiquid;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;

import java.util.List;
import java.util.stream.Collectors;

@BracketHandler(priority = 10)
@ZenRegister
public class LiquidBracketHandler implements IBracketHandler {

    private static final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), LiquidBracketHandler.class, "getFromString", String.class);

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2 && (tokens.get(0).getValue().equals("liquid") || tokens.get(0).getValue().equals("fluid")) && tokens.get(1).getValue().equals(":")) {
            return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, String.join("", tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.toList()))));
        }
        return null;
    }

    public static ILiquidStack getFromString(String name) {
        ILiquidStack out = BracketHandlerLiquid.getLiquid(name);
        return out == null ? new GhostLiquidStack(name) : out;
    }
}

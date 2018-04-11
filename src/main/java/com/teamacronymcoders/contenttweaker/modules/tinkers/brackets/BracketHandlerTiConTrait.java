package com.teamacronymcoders.contenttweaker.modules.tinkers.brackets;

import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
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

@BracketHandler
@ModOnly("tconstruct")
@ZenRegister
public class BracketHandlerTiConTrait implements IBracketHandler {

    public final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), TConTraitRepresentation.class, "getFromString", String.class);

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens == null || tokens.size() < 3 || !tokens.get(0).getValue().equalsIgnoreCase("ticontrait")) {
            return null;
        }
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, String.join("", tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.toList()))));
    }
}

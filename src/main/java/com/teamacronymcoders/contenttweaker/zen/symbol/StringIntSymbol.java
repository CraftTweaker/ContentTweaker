package com.teamacronymcoders.contenttweaker.zen.symbol;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInt;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

public class StringIntSymbol implements IZenSymbol {

    private final IEnvironmentGlobal environment;
    private final String name;
    private final int meta;
    private final IJavaMethod method;

    public StringIntSymbol(IEnvironmentGlobal environment, String name, int meta, IJavaMethod method) {
        this.environment = environment;
        this.name = name;
        this.meta = meta;
        this.method = method;
    }

    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name), new ExpressionInt(position, meta, ZenType.INT));
    }
}

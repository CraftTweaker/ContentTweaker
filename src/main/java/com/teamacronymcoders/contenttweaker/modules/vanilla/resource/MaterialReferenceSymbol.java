package com.teamacronymcoders.contenttweaker.modules.vanilla.resource;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

public class MaterialReferenceSymbol implements IZenSymbol {
    private IEnvironmentGlobal environment;
    private String name;

    public MaterialReferenceSymbol(IEnvironmentGlobal environment, String name) {
        this.environment = environment;
        this.name = name;
    }

    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new ExpressionCallStatic(position, environment, MaterialBracketHandler.getMethod(),
                new ExpressionString(position, name));
    }
}

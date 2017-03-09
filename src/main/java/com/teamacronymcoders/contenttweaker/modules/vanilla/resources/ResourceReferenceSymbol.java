package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

public class ResourceReferenceSymbol implements IZenSymbol {
    private IEnvironmentGlobal environment;
    private String name;
    private ResourceBracketHandler resourceBracketHandler;

    public ResourceReferenceSymbol(IEnvironmentGlobal environment, String name, ResourceBracketHandler handler) {
        this.environment = environment;
        this.name = name;
        this.resourceBracketHandler = handler;
    }

    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new ExpressionCallStatic(position, environment, resourceBracketHandler.getMethod(),
                new ExpressionString(position, name));
    }
}

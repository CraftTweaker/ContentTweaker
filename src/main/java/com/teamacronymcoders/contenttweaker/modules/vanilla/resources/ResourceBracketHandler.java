package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;

public abstract class ResourceBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    private String resourceType;

    public ResourceBracketHandler(String resourceType, Class clazz) {
        this.resourceType = resourceType;
        method = MineTweakerAPI.getJavaMethod(clazz, "get" + resourceType, String.class);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equalsIgnoreCase(resourceType) && tokens.get(1).getValue().equals(":")) {
                zenSymbol = find(environment, tokens, 2, tokens.size());
            }
        }

        return zenSymbol;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        return new ResourceReferenceSymbol(environment, valueBuilder.toString(), this);
    }

    public IJavaMethod getMethod() {
        return method;
    }
}

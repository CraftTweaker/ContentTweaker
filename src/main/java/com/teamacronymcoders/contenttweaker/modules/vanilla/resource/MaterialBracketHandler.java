package com.teamacronymcoders.contenttweaker.modules.vanilla.resource;

import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MaterialBracketHandler implements IBracketHandler {
    private static final Map<String, IMaterialDefinition> materialNames = new HashMap<>();
    private static IJavaMethod method;

    public MaterialBracketHandler() {
        if(method == null) {
            method = MineTweakerAPI.getJavaMethod(MaterialBracketHandler.class, "getMaterial", String.class);
        }
    }

    public IMaterialDefinition getMaterial(String name) {
        return materialNames.get(name.toLowerCase(Locale.US));
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equalsIgnoreCase("material") && tokens.get(1).getValue().equals(":")) {
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

        return new MaterialReferenceSymbol(environment, valueBuilder.toString());
    }

    @SuppressWarnings("deprecation")
    public static void rebuildEntityRegistry() {
        materialNames.clear();
        ForgeRegistries.BLOCKS.forEach(block -> {
            Material material = block.getMaterial(block.getDefaultState());
            materialNames.put(material.getClass().getName().toLowerCase(Locale.US), new MaterialDefinition(material));
        });
    }

    public static IJavaMethod getMethod() {
        return method;
    }
}

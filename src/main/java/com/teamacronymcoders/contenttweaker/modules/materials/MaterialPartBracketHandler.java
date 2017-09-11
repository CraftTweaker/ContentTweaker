package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.contenttweaker.api.utils.ResourceList;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

import java.util.List;

@BracketHandler
public class MaterialPartBracketHandler implements IBracketHandler {
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal iEnvironmentGlobal, List<Token> list) {
        return null;
    }
}

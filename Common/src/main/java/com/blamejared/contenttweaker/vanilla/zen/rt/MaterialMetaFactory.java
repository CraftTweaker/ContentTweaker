package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.util.MaterialRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(MaterialMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialMetaFactory {
    public static final String ZEN_NAME = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".MaterialMetaFactory";

    private MaterialMetaFactory() {}

    @ZenCodeType.Method
    public static MaterialReference factory(final ResourceLocation name) {
        return MaterialReference.of(Objects.requireNonNull(name), id -> MaterialRegistry.of().find(id));
    }
}

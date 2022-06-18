package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.util.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.SoundTypeReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(SoundTypeMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeMetaFactory {
    public static final String ZEN_NAME = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".SoundTypeMetaFactory";

    private SoundTypeMetaFactory() {}

    @ZenCodeType.Method
    public static SoundTypeReference factory(final ResourceLocation name) {
        return SoundTypeReference.of(Objects.requireNonNull(name), () -> SoundTypeRegistry.of().find(name));
    }
}

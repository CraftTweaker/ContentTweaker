package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".TierReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierReference extends Reference<Tier> {
    private TierReference(final ResourceLocation id) {
        super(VanillaObjectTypes.TIER, id);
    }

    @ZenCodeType.Method("of")
    public static TierReference of(final ResourceLocation name) {
        return new TierReference(Objects.requireNonNull(name));
    }
}

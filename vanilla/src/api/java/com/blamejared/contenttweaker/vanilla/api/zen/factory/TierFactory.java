package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".TierFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierFactory implements ObjectFactory<Tier> {
    public TierFactory() {}

    // Empty: will be filled by Forge and Fabric-specific expansions

    @Override
    public ObjectType<Tier> type() {
        return VanillaObjectTypes.TIER;
    }
}

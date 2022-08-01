package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.registry.CreativeTabRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".CreativeTabReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTabReference extends Reference<CreativeModeTab> {
    private CreativeTabReference(final String id) {
        super(VanillaObjectTypes.CREATIVE_TAB, CreativeTabRegistry.fromId(id));
    }

    @ZenCodeType.Method("of")
    public static CreativeTabReference of(final String id) {
        return new CreativeTabReference(id);
    }

    public static CreativeTabReference of(final ResourceLocation id) {
        return of(CreativeTabRegistry.toId(id));
    }

    @ZenCodeType.Getter("tabId")
    public String tabId() {
        return CreativeTabRegistry.toId(this.id());
    }
}

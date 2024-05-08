package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.CreativeTabProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardCreativeTabProperties;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".CreativeTabReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTabReference extends Reference<CreativeModeTab> {
    private static final ClassArchitect<CreativeTabProperties> ARCHITECT = ClassArchitect.of(CreativeTabReference.class);

    private CreativeTabReference(final ResourceLocation id) {
        super(VanillaObjectTypes.CREATIVE_TAB, id);
    }

    @ZenCodeType.Method("of")
    public static CreativeTabReference of(final ResourceLocation id) {
        return new CreativeTabReference(id);
    }

    @ZenCodeType.Getter("properties")
    public StandardCreativeTabProperties properties() {
        return this.findProperties(StandardCreativeTabProperties.class);
    }

    @ZenCodeType.Method("findProperties")
    public <T extends CreativeTabProperties> T findProperties(final Class<T> reifiedT) {
        return ARCHITECT.construct(reifiedT, this);
    }

}

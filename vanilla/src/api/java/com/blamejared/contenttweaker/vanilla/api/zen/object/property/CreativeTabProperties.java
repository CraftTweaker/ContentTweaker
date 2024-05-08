package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.CreativeTabProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class CreativeTabProperties {
    @BracketEnum("minecraft:tab/visibility")
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.helper.ItemTabVisibility")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public enum ItemTabVisibility {
        PARENT_AND_SEARCH,
        PARENT,
        SEARCH;

        private static final ItemTabVisibility[] VALUES = values();

        static ItemTabVisibility byIndex(final int index) {
            return VALUES[index];
        }
    }

    private final CreativeTabReference reference;
    private final String type;

    protected CreativeTabProperties(final CreativeTabReference reference, final String type) {
        this.reference = Objects.requireNonNull(reference);
        this.type = Objects.requireNonNull(type);
    }

    @ZenCodeType.Getter("type")
    public final String type() {
        return this.type;
    }

    protected final CreativeTabReference reference() {
        return this.reference;
    }

    protected final CreativeModeTab resolve() {
        return this.reference().get();
    }
}

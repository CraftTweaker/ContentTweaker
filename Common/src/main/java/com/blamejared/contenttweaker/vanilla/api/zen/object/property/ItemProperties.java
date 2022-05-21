package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.ItemProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class ItemProperties {
    private final ItemReference reference;
    private final String type;

    protected ItemProperties(final ItemReference reference, final String type) {
        this.reference = Objects.requireNonNull(reference);
        this.type = Objects.requireNonNull(type);
    }

    @ZenCodeType.Getter("type")
    public final String type() {
        return this.type;
    }

    protected final ItemReference reference() {
        return this.reference;
    }

    protected final Item resolve() {
        return this.reference().get();
    }
}

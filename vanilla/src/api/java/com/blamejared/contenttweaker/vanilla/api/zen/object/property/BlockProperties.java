package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.BlockProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class BlockProperties {
    private final BlockReference reference;
    private final String type;

    protected BlockProperties(final BlockReference reference, final String type) {
        this.reference = Objects.requireNonNull(reference);
        this.type = Objects.requireNonNull(type);
    }

    @ZenCodeType.Getter("type")
    public final String type() {
        return this.type;
    }

    protected final BlockReference reference() {
        return this.reference;
    }

    protected final Block resolve() {
        return this.reference().get();
    }
}

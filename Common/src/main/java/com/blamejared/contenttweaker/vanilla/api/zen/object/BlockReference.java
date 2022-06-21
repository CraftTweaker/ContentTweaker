package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.BlockProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".BlockReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockReference extends Reference<Block> {
    private static final ClassArchitect<BlockProperties> BLOCK_PROPERTIES_ARCHITECT = ClassArchitect.of(BlockReference.class);

    private BlockReference(final ResourceLocation id) {
        super(VanillaObjectTypes.BLOCK, id);
    }

    @ZenCodeType.Method("of")
    public static BlockReference of(final ResourceLocation id) {
        return new BlockReference(id);
    }

    @ZenCodeType.Getter("properties")
    public StandardBlockProperties properties() {
        return this.findProperties(StandardBlockProperties.class);
    }

    @ZenCodeType.Method("findProperties")
    public <T extends BlockProperties> T findProperties(final Class<T> reifiedT) {
        return BLOCK_PROPERTIES_ARCHITECT.construct(reifiedT, this);
    }
}

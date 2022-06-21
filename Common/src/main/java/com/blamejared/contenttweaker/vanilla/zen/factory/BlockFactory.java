package com.blamejared.contenttweaker.vanilla.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.block.BlockBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".BlockFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockFactory implements ObjectFactory<Block> {
    private static final ClassArchitect<BlockBuilder<?>> ARCHITECT = ClassArchitect.of(BiFunction.class);
    private static final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> CONVERT_AND_REGISTER = BlockFactory::convertAndRegister;

    public BlockFactory() {}

    private static <T extends Block> BlockReference convertAndRegister(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, resourceProvider));
        return BlockReference.of(holder.id());
    }

    @Override
    public ObjectType<Block> type() {
        return VanillaObjectTypes.BLOCK;
    }

    @ZenCodeType.Method("typed")
    public <T extends BlockBuilder<T>> T typed(final Class<T> reifiedT) {
        return ARCHITECT.construct(reifiedT, CONVERT_AND_REGISTER);
    }
}

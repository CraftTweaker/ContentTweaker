package com.blamejared.contenttweaker.vanilla.api.zen.builder.block;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.resource.BlockModel;
import com.blamejared.contenttweaker.vanilla.api.resource.BlockState;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.LootTable;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.BLOCK_BUILDER_PACKAGE + ".Basic")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BasicBlockBuilder extends BlockBuilder<BasicBlockBuilder> {
    private static final String WARN = "Unable to automatically generate loot table for block '%s' because the automatic block item has been disabled: an empty one will be generated instead";

    public BasicBlockBuilder(final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    public ObjectHolder<? extends Block> create(final ResourceLocation name, final Supplier<BlockBehaviour.Properties> builtProperties, final GenerateFlags flags) {
        return ObjectHolder.of(VanillaObjectTypes.BLOCK, name, () -> new Block(builtProperties.get()));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation assetName = new ResourceLocation(name.getNamespace(), "block/%s".formatted(name.getPath()));
        final ResourceLocation cubeAll = new ResourceLocation("block/cube_all");

        cotAssets.provideTemplated(PathHelper.texture(assetName), ContentTweakerVanillaConstants.blockTemplate("block"));
        cotAssets.provideFixed(PathHelper.blockState(name), BlockState.variant().singleModelFor("", assetName).finish(), BlockState.SERIALIZER);
        cotAssets.provideFixed(PathHelper.blockModel(name), BlockModel.of(cubeAll).texture("all", assetName), BlockModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.block(name, "Custom Block"), Language.SERIALIZER);

        if (flags.generateLootTable()) {
            final ResourceFragment cotData = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_DATA);
            this.selfLootTable(name, flags)
                    .or(() -> this.emptyTable(name, WARN::formatted))
                    .ifPresent(it -> cotData.provideFixed(PathHelper.blockLootTable(name), it, LootTable.SERIALIZER));
        }
    }
}

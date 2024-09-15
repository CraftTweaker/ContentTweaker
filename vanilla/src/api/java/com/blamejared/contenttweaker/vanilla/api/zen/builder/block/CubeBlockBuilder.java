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
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A Basic {@link BlockBuilder} implementation
 */
@Document("mods/ContentTweaker/vanilla/builder/block/Cube")
@ZenCodeType.Name(ContentTweakerVanillaConstants.BLOCK_BUILDER_PACKAGE + ".Cube")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CubeBlockBuilder extends BlockBuilder<CubeBlockBuilder> {
    public CubeBlockBuilder(final BiFunction<ObjectHolder<? extends Block>, Consumer<ResourceManager>, BlockReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    protected ObjectHolder<? extends Block> create(final ResourceLocation name, final Supplier<BlockBehaviour.Properties> builtProperties, final GenerateFlags flags) {
        return ObjectHolder.of(VanillaObjectTypes.BLOCK, name, () -> new Block(builtProperties.get()));
    }

    @Override
    protected void provideResources(final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceFragment cotData = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_DATA);

        final ResourceLocation assetName = new ResourceLocation(name.getNamespace(), "block/%s".formatted(name.getPath()));
        final ResourceLocation cubeAll = new ResourceLocation("block/cube_all");

        cotAssets.provideTemplated(PathHelper.texture(assetName), ContentTweakerVanillaConstants.blockTemplate("block"));
        cotAssets.provideFixed(PathHelper.blockState(name), BlockState.variant().singleModelFor("", assetName).finish(), BlockState.SERIALIZER);
        cotAssets.provideFixed(PathHelper.blockModel(name), BlockModel.of(cubeAll).texture("all", assetName), BlockModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.block(name, "Custom Block (" + name + ")"), Language.SERIALIZER);

        this.generateTable(name, flags, it -> cotData.provideFixed(PathHelper.blockLootTable(name), it, LootTable.SERIALIZER));
    }
}

package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.resource.ItemModel;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".BlockItem")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockItemBuilder extends ItemBuilder<BlockItemBuilder> {
    private static final class ReferencingBlockItem extends BlockItem {
        public ReferencingBlockItem(final BlockReference reference, final Supplier<Properties> properties) {
            super(reference.get(), properties.get());
        }
    }

    private BlockReference block;

    public BlockItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
        this.block = null;
    }

    @ZenCodeType.Method("block")
    public BlockItemBuilder block(final BlockReference reference) {
        this.block = reference;
        return this;
    }

    @Override
    public ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties) {
        Objects.requireNonNull(this.block);
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> new ReferencingBlockItem(this.block, builtProperties));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation blockModel = new ResourceLocation(this.block.id().getNamespace(), "block/%s".formatted(this.block.id().getPath()));

        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.of(blockModel), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Custom Block"), Language.SERIALIZER);
    }
}

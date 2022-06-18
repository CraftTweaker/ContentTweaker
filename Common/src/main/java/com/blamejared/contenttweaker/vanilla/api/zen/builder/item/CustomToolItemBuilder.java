package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.resource.ItemModel;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.resource.Tag;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".CustomTool")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CustomToolItemBuilder extends ToolItemBuilder<CustomToolItemBuilder> {
    private static final class CustomDiggerItem extends DiggerItem {
        CustomDiggerItem(final ToolData data, final ResourceLocation tag, final Supplier<Properties> properties) {
            super(data.baseAttackDamage(), data.attackSpeed(), data.tier().get(), TagKey.create(Registry.BLOCK_REGISTRY, tag), properties.get());
        }
    }

    private ResourceLocation tag;

    public CustomToolItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
        this.tag = null;
    }

    @ZenCodeType.Method("tag")
    public CustomToolItemBuilder tag(final ResourceLocation tag) {
        this.tag = Objects.requireNonNull(tag);
        return this;
    }

    @Override
    public ObjectHolder<? extends Item> createTool(final ResourceLocation name, final ToolData toolData, final Supplier<Item.Properties> builtProperties) {
        if (this.tag == null) {
            throw new IllegalStateException("Unable to create a custom tool without a tag for blocks to mine");
        }
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> new CustomDiggerItem(toolData, this.tag, builtProperties));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceFragment cotData = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_DATA);
        final ResourceLocation texture = new ResourceLocation(name.getNamespace(), "item/%s".formatted(name.getPath()));

        cotAssets.provideTemplated(PathHelper.texture(texture), ContentTweakerVanillaConstants.itemTemplate("item"));
        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.of(new ResourceLocation("item/handheld")).layer(0, texture), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Custom Tool"), Language.SERIALIZER);

        cotData.provideOrAlter(PathHelper.tag(VanillaObjectTypes.BLOCK, this.tag), Tag::of, Function.identity(), Tag.SERIALIZER);
    }
}

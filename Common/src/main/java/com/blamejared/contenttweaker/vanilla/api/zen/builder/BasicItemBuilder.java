package com.blamejared.contenttweaker.vanilla.api.zen.builder;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.resource.ItemModel;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".Basic")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BasicItemBuilder extends ItemBuilder<BasicItemBuilder> {
    public BasicItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    public ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties) {
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> this.build(builtProperties.get()));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation textureName = new ResourceLocation(name.getNamespace(), "item/%s".formatted(name.getPath()));

        cotAssets.provideTemplated(PathHelper.texture(textureName), "basic_item_texture.png"); // TODO("")
        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.ofGenerated().layer(0, textureName), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Example Item"), Language.SERIALIZER);
    }

    private Item build(final Item.Properties properties) {
        return new Item(properties);
    }
}

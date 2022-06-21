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
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".Axe")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class AxeToolItemBuilder extends ToolItemBuilder<AxeToolItemBuilder> {
    private static final class TotallyNotAnAxe extends AxeItem {
        TotallyNotAnAxe(final ToolData data, final Supplier<Properties> properties) {
            super(data.tier().get(), data.baseAttackDamage(), data.attackSpeed(), properties.get());
        }
    }

    public AxeToolItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    public ObjectHolder<? extends Item> createTool(final ResourceLocation name, final ToolData toolData, final Supplier<Item.Properties> builtProperties) {
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> new TotallyNotAnAxe(toolData, builtProperties));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation texture = new ResourceLocation(name.getNamespace(), "item/%s".formatted(name.getPath()));

        cotAssets.provideTemplated(PathHelper.texture(texture), ContentTweakerVanillaConstants.itemTemplate("axe"));
        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.of(new ResourceLocation("item/handheld")).layer(0, texture), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Custom Axe"), Language.SERIALIZER);
    }
}

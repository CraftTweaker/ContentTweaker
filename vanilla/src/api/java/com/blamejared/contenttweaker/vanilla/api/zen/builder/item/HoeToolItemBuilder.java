package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.vanilla.api.resource.ItemModel;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandle;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".Hoe")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class HoeToolItemBuilder extends ToolItemBuilder<HoeToolItemBuilder> {
    private static final class HoeHandles {
        static final MethodHandle INIT = Handles.trusted().linkConstructor(HoeItem.class, Tier.class, int.class, float.class, Item.Properties.class);

        private HoeHandles() {}
    }

    public HoeToolItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    protected ObjectHolder<? extends Item> createTool(final ResourceLocation name, final ToolData toolData, final Supplier<Item.Properties> builtProperties) {
        if (Mth.floor(toolData.baseAttackDamage()) != toolData.baseAttackDamage()) {
            throw new IllegalStateException("Unable to create a hoe item with a non-whole attack damage");
        }
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> this.build(toolData, builtProperties));
    }

    @Override
    protected void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation texture = new ResourceLocation(name.getNamespace(), "item/%s".formatted(name.getPath()));

        cotAssets.provideTemplated(PathHelper.texture(texture), ContentTweakerVanillaConstants.itemTemplate("hoe"));
        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.of(new ResourceLocation("item/handheld")).layer(0, texture), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Custom Hoe"), Language.SERIALIZER);
    }

    private Item build(final ToolData data, final Supplier<Item.Properties> properties) {
        return Handles.invoke(
                () -> (HoeItem) HoeHandles.INIT.invokeExact(data.tier().get(), (int) data.baseAttackDamage(), data.attackSpeed(), properties.get())
        );
    }
}

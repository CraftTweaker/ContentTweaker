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
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".Sword")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SwordItemBuilder extends ItemBuilder<SwordItemBuilder> {
    private Integer attackDamageBase;
    private Float attackDamageSpeed;
    private TierReference tier;

    public SwordItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
        this.attackDamageBase = null;
        this.attackDamageSpeed = null;
        this.tier = null;
    }

    @ZenCodeType.Method("baseAttackDamage")
    public SwordItemBuilder baseAttackDamage(final int attackDamageBase) {
        this.attackDamageBase = attackDamageBase;
        return this;
    }

    @ZenCodeType.Method("attackSpeed")
    public SwordItemBuilder attackSpeed(final float attackSpeed) {
        this.attackDamageSpeed = attackSpeed;
        return this;
    }

    @ZenCodeType.Method("tier")
    public SwordItemBuilder tier(final TierReference tier) {
        this.tier = Objects.requireNonNull(tier);
        return this;
    }

    @Override
    public ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties) {
        if (this.tier == null) {
            throw new IllegalStateException("Unable to create a sword item without a tier");
        }
        if (this.attackDamageBase == null) {
            throw new IllegalStateException("Unable to create a sword without a base attack damage");
        }
        if (this.attackDamageSpeed == null) {
            throw new IllegalStateException("Unable to create a sword item without attack speed");
        }
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> new SwordItem(this.tier.get(), this.attackDamageBase, this.attackDamageSpeed, builtProperties.get()));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        final ResourceFragment cotAssets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final ResourceLocation texture = new ResourceLocation(name.getNamespace(), "item/%s".formatted(name.getPath()));

        cotAssets.provideTemplated(PathHelper.texture(texture), ContentTweakerVanillaConstants.itemTemplate("katana"));
        cotAssets.provideFixed(PathHelper.itemModel(name), ItemModel.of(new ResourceLocation("item/handheld")).layer(0, texture), ItemModel.SERIALIZER);
        cotAssets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.item(name, "Custom Sword"), Language.SERIALIZER);
    }
}

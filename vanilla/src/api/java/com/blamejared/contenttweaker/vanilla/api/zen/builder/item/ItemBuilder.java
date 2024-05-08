package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.ContentTweakerLoggers;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.FoodItemProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.ItemProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardItemProperties;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.Optionull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.VarHandle;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".ItemBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class ItemBuilder<T extends ItemBuilder<T>> {
    private record PropertyPair(StandardItemProperties standard, FoodItemProperties food) {
        PropertyPair standard(final StandardItemProperties properties) {
            return new PropertyPair(properties, this.food);
        }

        PropertyPair food(final FoodItemProperties properties) {
            return new PropertyPair(this.standard, properties);
        }
    }

    private static final class PropertySettingHandles {
        static final VarHandle REQUIRED_FEATURES = Handles.trusted().linkField(
                HandleAccess.directAccess(),
                Item.Properties.class,
                Handles.Names.of("field_40210", "f_244559_", "requiredFeatures"),
                FeatureFlagSet.class
        );

        private PropertySettingHandles() {}
    }

    private final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager;

    private PropertyPair cloningProperties;

    private Integer maxStackSize;
    private Integer maxDamage;
    private ItemReference remainder;
    private Rarity rarity;
    private Boolean fireResistance;
    private FeatureFlagSet requiredFeatures;

    private Integer nutrition;
    private Float saturation;
    private Boolean meat;
    private Boolean alwaysEat;
    private Boolean fast;
    // TODO("Effects")

    protected ItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        this.registrationManager = Objects.requireNonNull(registrationManager, "registrationManager");
        this.cloningProperties = new PropertyPair(null, null);
        this.maxStackSize = null;
        this.maxDamage = null;
        this.remainder = null;
        this.rarity = null;
        this.fireResistance = null;
        this.requiredFeatures = null;
        this.nutrition = null;
        this.saturation = null;
        this.meat = null;
        this.alwaysEat = null;
        this.fast = null;
        // TODO("Effects")
    }

    @ZenCodeType.Method("cloning")
    public T cloning(final ItemProperties properties) {
        Objects.requireNonNull(properties);
        final StandardItemProperties standard = "standard".equals(properties.type())? (StandardItemProperties) properties : null;
        final FoodItemProperties food = "food".equals(properties.type())? (FoodItemProperties) properties : null;
        if (standard == null && food == null) {
            throw new IllegalArgumentException("Unknown set of properties " + properties.type() + " to clone from");
        }
        return this.cloning(standard, food);
    }

    @ZenCodeType.Method("cloning")
    public T cloning(@ZenCodeType.Nullable final StandardItemProperties standard, @ZenCodeType.Nullable final FoodItemProperties food) {
        if (standard == null && food == null) {
            throw new IllegalArgumentException("Unable to clone from no property set for both standard and food");
        }
        if ((standard != null && this.cloningProperties.standard() != null) || (food != null && this.cloningProperties.food() != null)) {
            throw new IllegalStateException("Already specified properties to clone from");
        }
        if (standard != null) {
            this.cloningProperties = this.cloningProperties.standard(standard);
        }
        if (food != null) {
            this.cloningProperties = this.cloningProperties.food(food);
        }
        return this.self();
    }

    @ZenCodeType.Method("stacksTo")
    public T stacksTo(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid stack size " + size);
        }
        this.maxStackSize = size;
        return this.self();
    }

    @ZenCodeType.Method("durability")
    public T durability(final int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Invalid damage " + damage);
        }
        this.maxDamage = damage;
        return this.self();
    }

    @ZenCodeType.Method("craftRemainder")
    public T craftRemainder(final ItemReference remainder) {
        this.remainder = remainder;
        return this.self();
    }

    @ZenCodeType.Method("rarity")
    public T rarity(final Rarity rarity) {
        this.rarity = rarity;
        return this.self();
    }

    @ZenCodeType.Method("fireResistant")
    public T fireResistant(final boolean fireResistance) {
        this.fireResistance = fireResistance;
        return this.self();
    }

    @ZenCodeType.Method("requiredFeatures")
    public T requiredFeatures(final FeatureFlagSet set) {
        this.requiredFeatures = set;
        return this.self();
    }

    @ZenCodeType.Method("nutrition")
    public T nutrition(final int nutrition) {
        if (nutrition <= 0) {
            throw new IllegalArgumentException("Invalid nutrition " + nutrition);
        }
        this.nutrition = nutrition;
        return this.self();
    }

    @ZenCodeType.Method("saturationMod")
    public T saturationMod(final float modifier) {
        if (modifier < 0.0) {
            throw new IllegalArgumentException("Invalid saturation modifier " + modifier);
        }
        this.saturation = modifier;
        return this.self();
    }

    @ZenCodeType.Method("meat")
    public T meat(final boolean meat) {
        this.meat = meat;
        return this.self();
    }

    @ZenCodeType.Method("canAlwaysEat")
    public T canAlwaysEat(final boolean canEat) {
        this.alwaysEat = canEat;
        return this.self();
    }

    @ZenCodeType.Method("fastFood")
    public T fastFood(final boolean fast) {
        this.fast = fast;
        return this.self();
    }

    // TODO("Effects")

    @ZenCodeType.Method("build")
    public final ItemReference build(final String name) {
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        return this.registrationManager.apply(this.create(id, this.createPropertiesCreator(id)), manager -> this.provideResources(id, manager));
    }

    protected abstract ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties);

    protected abstract void provideResources(final ResourceLocation name, final ResourceManager manager);

    private Supplier<Item.Properties> createPropertiesCreator(final ResourceLocation name) {
        this.verifyProperties(name);
        return this::createOverriddenProperties;
    }

    private void verifyProperties(final ResourceLocation name) {
        if (this.maxDamage != null) {
            if (this.maxStackSize != null && this.maxStackSize != 1) {
                throw new IllegalStateException("An item with durability can only be stacked up to 1, but found " + this.maxStackSize);
            } else if (this.maxStackSize == null) {
                ContentTweakerLoggers.user().warn("Item {} has durability but no maximum stack size set: assuming 1", name);
            }
        }
        if (this.maxStackSize != null && this.maxStackSize > 64) {
            ContentTweakerLoggers.user().warn("Item {} stacks with a stack size bigger than 64: this might cause further problems down the line", name);
        }
    }

    private Item.Properties createOverriddenProperties() {
        final Item.Properties properties = new Item.Properties();
        this.apply(this.maxStackSize, PropertyPair::standard, StandardItemProperties::maxStackSize, properties::stacksTo);
        this.apply(this.maxDamage, PropertyPair::standard, StandardItemProperties::maxDamage, properties::durability);
        this.apply(this.remainder, PropertyPair::standard, StandardItemProperties::craftingRemainingItem, properties::craftRemainder, ItemReference::get);
        this.apply(this.rarity, PropertyPair::standard, StandardItemProperties::rarity, properties::rarity);
        this.apply(this.fireResistance, PropertyPair::standard, StandardItemProperties::isFireResistant, ifTrue(properties::fireResistant));
        this.apply(this.requiredFeatures, PropertyPair::standard, StandardItemProperties::requiredFeatures, it -> PropertySettingHandles.REQUIRED_FEATURES.set(properties, it));

        if (this.isFood()) {
            final FoodProperties.Builder foodProperties = new FoodProperties.Builder();
            this.apply(this.nutrition, PropertyPair::food, FoodItemProperties::nutrition, foodProperties::nutrition);
            this.apply(this.saturation, PropertyPair::food, FoodItemProperties::saturationModifier, foodProperties::saturationMod);
            this.apply(this.meat, PropertyPair::food, FoodItemProperties::isMeat, ifTrue(foodProperties::meat));
            this.apply(this.alwaysEat, PropertyPair::food, FoodItemProperties::canAlwaysEat, ifTrue(foodProperties::alwaysEat));
            this.apply(this.fast, PropertyPair::food, FoodItemProperties::fastFood, ifTrue(foodProperties::fast));

            properties.food(foodProperties.build());
        }

        return properties;
    }

    private <P, I extends ItemProperties> void apply(
            final P property,
            final Function<PropertyPair, I> chooser,
            final Function<I, P> getter,
            final Consumer<P> consumer
    ) {
        this.apply(property, chooser, getter, consumer, Function.identity());
    }

    private <P, I extends ItemProperties, L> void apply(
            final P property,
            final Function<PropertyPair, I> chooser,
            final Function<I, P> getter,
            final Consumer<L> consumer,
            final Function<P, L> converter
    ) {
        final P target = this.determineProperty(property, this.merge(chooser, getter));
        if (target != null) {
            consumer.accept(converter.apply(target));
        }
    }

    private <P> P determineProperty(final P property, final Function<PropertyPair, P> getter) {
        return property != null? property : getter.apply(this.cloningProperties);
    }

    private <P, I extends ItemProperties> Function<PropertyPair, P> merge(final Function<PropertyPair, I> chooser, final Function<I, P> getter) {
        return pair -> Optionull.map(chooser.apply(pair), getter);
    }

    private Consumer<Boolean> ifTrue(final Runnable run) {
        return b -> {
            if (b) {
                run.run();
            }
        };
    }

    private boolean isFood() {
        return Stream.of(
                this.cloningProperties.food(),
                this.nutrition,
                this.saturation,
                this.meat,
                this.alwaysEat,
                this.fast
                // TODO("Effects")
        ).anyMatch(Objects::nonNull);
    }

    private T self() {
        return GenericUtil.uncheck(this);
    }
}

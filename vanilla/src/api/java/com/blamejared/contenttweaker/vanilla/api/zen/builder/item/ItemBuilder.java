package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.FoodItemProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.ItemProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardItemProperties;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".ItemBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class ItemBuilder<T extends ItemBuilder<T>> {
    private final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager;

    private StandardItemProperties cloningProperties;
    private FoodItemProperties cloningFood;

    private Integer maxStackSize;
    private Integer maxDamage;
    private ItemReference remainder;
    private CreativeTabReference group;
    private Rarity rarity;
    private Boolean fireResistance;

    private Integer nutrition;
    private Float saturation;
    private Boolean meat;
    private Boolean alwaysEat;
    private Boolean fast;
    // TODO("Effects")

    protected ItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        this.registrationManager = Objects.requireNonNull(registrationManager, "registrationManager");
        this.cloningProperties = null;
        this.cloningFood = null;
        this.maxStackSize = null;
        this.maxDamage = null;
        this.remainder = null;
        this.group = null;
        this.rarity = null;
        this.fireResistance = null;
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
        if ((standard != null && this.cloningProperties != null) || (food != null && this.cloningFood != null)) {
            throw new IllegalStateException("Already specified properties to clone from");
        }
        if (standard != null) {
            this.cloningProperties = standard;
        }
        if (food != null) {
            this.cloningFood = food;
        }
        return this.self();
    }

    @ZenCodeType.Method("stacksTo")
    public T stacksTo(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid stack size " + size);
        }
        if (size > 64) {
            CraftTweakerAPI.LOGGER.warn("Identified stack size bigger than 64: this will likely not work");
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

    @ZenCodeType.Method("tab")
    public T tab(final CreativeTabReference tab) {
        this.group = tab;
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

    @ZenCodeType.Method("nutrition")
    public T nutrition(final int nutrition) {
        if (this.nutrition <= 0) {
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
        return this.registrationManager.apply(this.create(id, this::makeProperties), manager -> this.provideResources(id, manager));
    }

    public abstract ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties);

    public abstract void provideResources(final ResourceLocation name, final ResourceManager manager);

    private Item.Properties makeProperties() {
        final Item.Properties props = new Item.Properties();
        if (this.cloningProperties != null) {
            this.cloneInto(this.cloningProperties, props);
        }
        if (this.cloningFood != null) {
            this.cloneInto(this.cloningFood, props);
        }
        this.apply(
                props,
                this.maxDamage,
                this.maxStackSize,
                this.remainder,
                this.group,
                this.rarity,
                this.fireResistance,
                this.nutrition,
                this.saturation,
                this.meat,
                this.alwaysEat,
                this.fast
        );
        return props;
    }

    private void cloneInto(final StandardItemProperties properties, final Item.Properties out) {
        this.apply(
                out,
                properties.maxDamage(),
                properties.maxStackSize(),
                properties.craftingRemainingItem(),
                properties.category(),
                properties.rarity(),
                properties.isFireResistant(),
                null,
                null,
                null,
                null,
                null
        );
    }

    private void cloneInto(final FoodItemProperties properties, final Item.Properties out) {
        this.apply(
                out,
                null,
                null,
                null,
                null,
                null,
                null,
                properties.nutrition(),
                properties.saturationModifier(),
                properties.isMeat(),
                properties.canAlwaysEat(),
                properties.fastFood()
                // TODO("Effects")
        );
    }

    private void apply(
            final Item.Properties out,
            final Integer durability,
            final Integer stack,
            final ItemReference remainder,
            final CreativeTabReference tab,
            final Rarity rarity,
            final Boolean fire,
            final Integer nutrition,
            final Float saturation,
            final Boolean meat,
            final Boolean alwaysEat,
            final Boolean fast
            // TODO("Effects")
    ) {
        if (durability != null) {
            out.durability(durability);
        }
        if (stack != null) {
            out.stacksTo(stack);
        }
        if (remainder != null) {
            out.craftRemainder(remainder.get());
        }
        if (tab != null) {
            out.tab(tab.get());
        }
        if (rarity != null) {
            out.rarity(rarity);
        }
        if (fire != null && fire) {
            out.fireResistant();
        }
        if (nutrition != null || saturation != null || meat != null || alwaysEat != null|| fast != null /*|| TODO("Effects")*/) {
            final FoodProperties.Builder food = new FoodProperties.Builder();
            if (nutrition != null) {
                food.nutrition(nutrition);
            }
            if (saturation != null) {
                food.saturationMod(saturation);
            }
            if (meat != null && meat) {
                food.meat();
            }
            if (alwaysEat != null && alwaysEat) {
                food.alwaysEat();
            }
            if (fast != null && fast) {
                food.fast();
            }
            // TODO("Effects")
            out.food(food.build());
        }
    }

    private T self() {
        return GenericUtil.uncheck(this);
    }
}

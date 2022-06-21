package com.blamejared.contenttweaker.vanilla.api.zen.builder.item;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".ToolBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class ToolItemBuilder<T extends ToolItemBuilder<T>> extends ItemBuilder<T> {
    protected record ToolData(Supplier<Tier> tier, float baseAttackDamage, float attackSpeed) {}

    private Float attackDamageBase;
    private Float attackDamageSpeed;
    private TierReference tier;

    protected ToolItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
        this.attackDamageBase = null;
        this.attackDamageSpeed = null;
        this.tier = null;
    }

    @ZenCodeType.Method("baseAttackDamage")
    public T baseAttackDamage(final float attackDamageBase) {
        this.attackDamageBase = attackDamageBase;
        return this.self();
    }

    @ZenCodeType.Method("attackSpeed")
    public T attackSpeed(final float attackSpeed) {
        this.attackDamageSpeed = attackSpeed;
        return this.self();
    }

    @ZenCodeType.Method("tier")
    public T tier(final TierReference tier) {
        this.tier = Objects.requireNonNull(tier);
        return this.self();
    }

    @Override
    public final ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties) {
        if (this.tier == null) {
            throw new IllegalStateException("Unable to create a tool item without a tier");
        }
        if (this.attackDamageBase == null) {
            throw new IllegalStateException("Unable to create a tool without a base attack damage");
        }
        if (this.attackDamageSpeed == null) {
            throw new IllegalStateException("Unable to create a tool item without attack speed");
        }
        return this.createTool(name, new ToolData(this.tier::get, this.attackDamageBase, this.attackDamageSpeed), builtProperties);
    }

    public abstract ObjectHolder<? extends Item> createTool(final ResourceLocation name, final ToolData toolData, final Supplier<Item.Properties> builtProperties);

    private T self() {
        return GenericUtil.uncheck(this);
    }
}

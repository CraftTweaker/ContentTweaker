package com.blamejared.contenttweaker.fabric.api.util;

import com.google.common.base.Suppliers;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("ClassCanBeRecord") // remapping
public final class CustomTier implements Tier {
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantmentValue;
    private final Supplier<Ingredient> reference;

    private CustomTier(final int uses, final float speed, final float attackDamageBonus, final int level, final int enchantmentValue, final Supplier<Ingredient> reference) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantmentValue = enchantmentValue;
        this.reference = Suppliers.memoize(Objects.requireNonNull(reference)::get);
    }

    public static CustomTier of(final int uses, final float speed, final float attackDamageBonus, final int level, final int enchantmentValue, final Supplier<Ingredient> reference) {
        return new CustomTier(uses, speed, attackDamageBonus, level, enchantmentValue, Objects.requireNonNull(reference));
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.reference.get();
    }
}

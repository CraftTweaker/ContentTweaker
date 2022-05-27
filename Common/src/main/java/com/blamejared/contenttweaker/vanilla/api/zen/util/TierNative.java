package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

@NativeTypeRegistration(value = Tier.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".Tier")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierNative {
    private TierNative() {}

    @ZenCodeType.Getter("uses")
    public static int uses(final Tier $this) {
        return $this.getUses();
    }

    @ZenCodeType.Getter("speed")
    public static float speed(final Tier $this) {
        return $this.getSpeed();
    }

    @ZenCodeType.Getter("attackDamageBonus")
    public static float attackDamageBonus(final Tier $this) {
        return $this.getAttackDamageBonus();
    }

    @ZenCodeType.Getter("level")
    public static int level(final Tier $this) {
        return $this.getLevel();
    }

    @ZenCodeType.Getter("enchantmentValue")
    public static int enchantmentValue(final Tier $this) {
        return $this.getEnchantmentValue();
    }

    // TODO("repairIngredient")
}

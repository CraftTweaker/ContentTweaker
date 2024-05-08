package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.Optionull;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.StandardItemProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class StandardItemProperties extends ItemProperties {
    public StandardItemProperties(final ItemReference reference) {
        super(reference, "standard");
    }

    public int maxStackSize() {
        return this.resolve().getMaxStackSize();
    }

    public int maxDamage() {
        return this.resolve().getMaxDamage();
    }

    public ItemReference craftingRemainingItem() {
        return Optionull.mapOrDefault(
                this.resolve().getCraftingRemainingItem(),
                it -> ItemReference.of(BuiltInRegistries.ITEM.getKey(it)),
                ItemReference.AIR
        );
    }

    public Rarity rarity() {
        return this.resolve().getRarity(this.resolve().getDefaultInstance());
    }

    public boolean isFireResistant() {
        return this.resolve().isFireResistant();
    }

    public FeatureFlagSet requiredFeatures() {
        return this.resolve().requiredFeatures();
    }
}

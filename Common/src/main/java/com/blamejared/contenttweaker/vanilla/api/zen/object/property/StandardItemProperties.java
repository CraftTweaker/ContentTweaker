package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTab;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.StandardItemProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class StandardItemProperties extends ItemProperties {
    public StandardItemProperties(final ItemReference reference) {
        super(reference, "standard");
    }

    @ZenCodeType.Getter("maxStackSize")
    public int maxStackSize() {
        return this.resolve().getMaxStackSize();
    }

    @ZenCodeType.Getter("maxDamage")
    public int maxDamage() {
        return this.resolve().getMaxDamage();
    }

    @ZenCodeType.Getter("craftingRemainingItem")
    public ItemReference craftingRemainingItem() {
        return Optional.ofNullable(this.resolve().getCraftingRemainingItem())
                .map(Registry.ITEM::getKey)
                .map(ItemReference::of)
                .orElse(ItemReference.AIR);
    }

    @ZenCodeType.Getter("category")
    public CreativeTab category() {
        return CreativeTab.wrap(this.resolve().getItemCategory());
    }

    @ZenCodeType.Getter("rarity")
    public Rarity rarity() {
        return this.resolve().getRarity(this.resolve().getDefaultInstance());
    }

    @ZenCodeType.Getter("isFireResistant")
    public boolean isFireResistant() {
        return this.resolve().isFireResistant();
    }
}

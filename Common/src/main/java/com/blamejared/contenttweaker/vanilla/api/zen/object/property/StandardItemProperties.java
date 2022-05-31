package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
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
    @ZenCodeType.Nullable
    public CreativeTabReference category() {
        final CreativeModeTab tab = this.resolve().getItemCategory();
        if (tab == null) {
            return null;
        }
        return CreativeTabReference.of(((CreativeModeTabAccessor) tab).contenttweaker$langId(), () -> tab);
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

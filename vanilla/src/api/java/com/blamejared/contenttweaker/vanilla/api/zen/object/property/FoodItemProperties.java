package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.food.FoodProperties;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.FoodItemProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FoodItemProperties extends ItemProperties {
    public FoodItemProperties(final ItemReference reference) {
        super(reference, "food");
    }

    public int nutrition() {
        return this.resolveProperties().getNutrition();
    }

    public float saturationModifier() {
        return this.resolveProperties().getSaturationModifier();
    }

    public boolean isMeat() {
        return this.resolveProperties().isMeat();
    }

    public boolean canAlwaysEat() {
        return this.resolveProperties().canAlwaysEat();
    }

    public boolean fastFood() {
        return this.resolveProperties().isFastFood();
    }

    // TODO("Effects")

    private FoodProperties resolveProperties() {
        return Objects.requireNonNull(this.resolve().getFoodProperties(), () -> "The item " + this.reference().id() + " is not a food");
    }
}

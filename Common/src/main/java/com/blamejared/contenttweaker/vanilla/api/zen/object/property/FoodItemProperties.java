package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.food.FoodProperties;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.FoodItemProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FoodItemProperties extends ItemProperties {
    public FoodItemProperties(final ItemReference reference) {
        super(reference, "food");
    }

    @ZenCodeType.Getter("nutrition")
    public int nutrition() {
        return this.resolveProperties().getNutrition();
    }

    @ZenCodeType.Getter("saturationModifier")
    public float saturationModifier() {
        return this.resolveProperties().getSaturationModifier();
    }

    @ZenCodeType.Getter("isMeat")
    public boolean isMeat() {
        return this.resolveProperties().isMeat();
    }

    @ZenCodeType.Getter("canAlwaysEat")
    public boolean canAlwaysEat() {
        return this.resolveProperties().canAlwaysEat();
    }

    @ZenCodeType.Getter("fastFood")
    public boolean fastFood() {
        return this.resolveProperties().isFastFood();
    }

    // TODO("Effects")

    private FoodProperties resolveProperties() {
        return this.resolve().getFoodProperties();
    }
}

package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import slimeknights.mantle.util.RecipeMatchRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.tconstruct.trait")
@ZenRegister
@ModOnly("tconstruct")
public class TConTraitRepresentation {
    private final ITrait trait;

    public TConTraitRepresentation(ITrait trait) {
        this.trait = trait;
    }

    @SuppressWarnings("unused")
    public static TConTraitRepresentation getFromString(String identifier) {
        ITrait trait = TinkerRegistry.getTrait(identifier);
        if (trait == null) {
            CraftTweakerAPI.logError("Cannot identify trait " + "<ticontrait:" + identifier + ">");
            return null;
        }
        return new TConTraitRepresentation(trait);
    }


    @ZenMethod
    public void addItem(IIngredient item, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 1) int amountMatched) {

        if (!(trait instanceof RecipeMatchRegistry)) {
            CraftTweakerAPI.logError("Cannot add item " + item.toCommandString() + " to trait " + toCommandString());
            return;
        }

        RecipeMatchRegistry trait = (RecipeMatchRegistry) this.trait;
        if (item instanceof IItemStack) {
            trait.addItem(CraftTweakerMC.getItemStack(item), amountNeeded, amountMatched);
        } else if (item instanceof IOreDictEntry) {
            trait.addItem(((IOreDictEntry) item).getName(), amountNeeded, amountMatched);
        } else {
            for (IItemStack itemStack : item.getItems()) {
                addItem(itemStack, amountNeeded, amountMatched);
            }
        }

    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return trait.getIdentifier();
    }

    @ZenMethod
    public String toCommandString() {
        return "<ticontrait:" + trait.getIdentifier() + ">";
    }


    public ITrait getTrait() {
        return trait;
    }
}

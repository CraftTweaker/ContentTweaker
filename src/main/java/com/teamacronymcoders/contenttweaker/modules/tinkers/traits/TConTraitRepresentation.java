package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.mantle.util.RecipeMatchRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import stanhebben.zenscript.annotations.*;

import java.util.Arrays;

@ZenClass("mods.contenttweaker.tconstruct.Trait")
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
        if(trait == null) {
            CraftTweakerAPI.logError("Cannot identify trait " + "<ticontrait:" + identifier + ">");
            return null;
        }
        return new TConTraitRepresentation(trait);
    }


    @ZenMethod
    public void addItem(IIngredient item, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 1) int amountMatched) {

        if(!(trait instanceof RecipeMatchRegistry)) {
            CraftTweakerAPI.logError("Cannot add item " + item.toCommandString() + " to trait " + toCommandString());
            return;
        }

        RecipeMatchRegistry trait = (RecipeMatchRegistry) this.trait;
        if(item instanceof IItemStack) {
            trait.addItem(CraftTweakerMC.getItemStack(item), amountNeeded, amountMatched);
        } else if(item instanceof IOreDictEntry) {
            trait.addItem(((IOreDictEntry) item).getName(), amountNeeded, amountMatched);
        } else {
            for (IItemStack itemStack : item.getItems()) {
                addItem(itemStack, amountNeeded, amountMatched);
            }
        }
    }

    @ZenMethod
    public void addMultiItem(int amountMatched, IItemStack... items) {
        if(!(trait instanceof RecipeMatchRegistry)){
            CraftTweakerAPI.logError("Cannot add items " + Arrays.toString(items) + " to trait " + toCommandString());
            return;
        }
        RecipeMatchRegistry recipeMatchRegistry = (RecipeMatchRegistry) this.trait;
        recipeMatchRegistry.addRecipeMatch(new RecipeMatch.ItemCombination(amountMatched, CraftTweakerMC.getItemStacks(items)));
    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return trait.getIdentifier();
    }

    @ZenGetter("commandString")
    public String toCommandString() {
        return "<ticontrait:" + trait.getIdentifier() + ">";
    }

    @ZenMethod
    public TConTraitDataRepresentation getData(IItemStack itemStack) {
        if(trait instanceof ModifierTrait) {
            return new TConTraitDataRepresentation(((ModifierTrait) trait).getData(CraftTweakerMC.getItemStack(itemStack)));
        }
        CraftTweakerAPI.logError("Trait " + trait.getIdentifier() + " is not applicable to the getData function!");
        return null;
    }


    public ITrait getTrait() {
        return trait;
    }
}

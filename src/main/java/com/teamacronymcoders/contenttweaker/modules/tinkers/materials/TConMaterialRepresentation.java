package com.teamacronymcoders.contenttweaker.modules.tinkers.materials;

import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;


/**
 * Material Representation, returned from the materialBuilder's register method and the Bracket handler
 */

@ZenClass("mods.contenttweaker.tconstruct.Material")
@ZenRegister
@ModOnly("tconstruct")
public class TConMaterialRepresentation {
    private final Material material;

    public TConMaterialRepresentation(Material material) {
        this.material = material;
    }

    @SuppressWarnings("unused")
    public static TConMaterialRepresentation getFromString(String identifier) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material == null || material == Material.UNKNOWN) {
            CraftTweakerAPI.logError("Cannot identify material " + "<ticonmaterial:" + identifier + ">");
            return null;
        }
        return new TConMaterialRepresentation(material);
    }

    @ZenMethod
    public void addItem(IIngredient item, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 144) int amountMatched) {
        if (item instanceof IItemStack) {
            material.addItem(CraftTweakerMC.getItemStack(item), amountNeeded, amountMatched);
        } else if (item instanceof IOreDictEntry) {
            material.addItem(((IOreDictEntry) item).getName(), amountNeeded, amountMatched);
        } else {
            for (IItemStack itemStack : item.getItems()) {
                addItem(itemStack, amountNeeded, amountMatched);
            }
        }
    }

    @ZenMethod
    public void addTrait(TConTraitRepresentation trait) {
        TinkerRegistry.addMaterialTrait(material, trait.getTrait(), null);
    }

    public Material getMaterial() {
        return material;
    }

    @ZenGetter("commandString")
    public String toCommandString() {
        return "<ticonmaterial:" + material.getIdentifier() + ">";
    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return material.identifier;
    }
}

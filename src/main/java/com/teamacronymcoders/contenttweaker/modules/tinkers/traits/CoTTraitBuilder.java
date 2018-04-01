package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.CoTRecipeMatch;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.contenttweaker.tconstruct.TraitBuilder")
@ZenRegister
@ModOnly("tconstruct")
public class CoTTraitBuilder {

    @ZenProperty
    public String identifier;

    @ZenProperty
    public int color = 0xffffff;

    @ZenProperty
    public int maxLevel = 0;

    @ZenProperty
    public int countPerLevel = 0;

    @ZenProperty
    public boolean hidden = false;

    @ZenProperty
    public TraitFunctions.afterBlockBreak afterBlockBreak = null;

    @ZenProperty
    public TraitFunctions.beforeBlockBreak beforeBlockBreak = null;

    @ZenProperty
    public TraitFunctions.blockHarvestDrops onBlockHarvestDrops = null;

    @ZenProperty
    public TraitFunctions.damage calcDamage = null;

    @ZenProperty
    public TraitFunctions.isCriticalHit calcCrit = null;

    @ZenProperty
    public TraitFunctions.miningSpeed getMiningSpeed = null;

    @ZenProperty
    public TraitFunctions.onHit onHit = null;

    @ZenProperty
    public TraitFunctions.onUpdate onUpdate = null;

    @ZenProperty
    public TraitFunctions.afterHit afterHit = null;

    @ZenProperty
    public TraitFunctions.knockBack calcKnockBack = null;

    @ZenProperty
    public TraitFunctions.onBlock onBlock = null;

    @ZenProperty
    public TraitFunctions.onToolDamage onToolDamage = null;

    @ZenProperty
    public TraitFunctions.onToolHeal calcToolHeal = null;

    @ZenProperty
    public TraitFunctions.onToolRepair onToolRepair = null;

    @ZenProperty
    public TraitFunctions.onPlayerHurt onPlayerHurt = null;

    public TraitFunctions.canApplyTogether canApplyTogether = null;

    private List<CoTRecipeMatch> recipeMatches = new ArrayList<>();

    public CoTTraitBuilder(String identifier) {

        this.identifier = identifier;
    }

    @ZenMethod
    public static CoTTraitBuilder create(String identifier) {
        return new CoTTraitBuilder(identifier);
    }

    @ZenMethod
    public void addItem(IIngredient ingredient, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 1) int amountMatched) {
        recipeMatches.add(new CoTRecipeMatch(ingredient, amountMatched, amountNeeded));
    }

    @ZenMethod
    public void removeItem(IItemStack itemStack) {
        recipeMatches.removeIf(coTRecipeMatch -> coTRecipeMatch.matches(itemStack));
    }

    @ZenMethod
    public TConTraitRepresentation register() {
        CoTTrait trait = new CoTTrait(identifier, color, maxLevel, countPerLevel);
        trait.afterBlockBreak = this.afterBlockBreak;
        trait.beforeBlockBreak = this.beforeBlockBreak;
        trait.onBlockHarvestDrops = this.onBlockHarvestDrops;
        trait.calcDamage = this.calcDamage;
        trait.calcCrit = this.calcCrit;
        trait.getMiningSpeed = this.getMiningSpeed;
        trait.onHit = this.onHit;
        trait.onUpdate = this.onUpdate;
        trait.afterHit = this.afterHit;
        trait.calcKnockBack = this.calcKnockBack;
        trait.onBlock = this.onBlock;
        trait.onToolDamage = this.onToolDamage;
        trait.calcToolHeal = this.calcToolHeal;
        trait.onToolRepair = this.onToolRepair;
        trait.onPlayerHurt = this.onPlayerHurt;
        trait.hidden = this.hidden;
        trait.canApplyTogether = this.canApplyTogether;

        for (CoTRecipeMatch recipeMatch : recipeMatches) {
            trait.addItem(recipeMatch);
        }

        TinkerRegistry.addTrait(trait);

        return new TConTraitRepresentation(trait);
    }
}

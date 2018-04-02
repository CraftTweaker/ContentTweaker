package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.CoTRecipeMatch;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.Functions;
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
    public Functions.AfterBlockBreak afterBlockBreak = null;

    @ZenProperty
    public Functions.BeforeBlockBreak beforeBlockBreak = null;

    @ZenProperty
    public Functions.BlockHarvestDrops onBlockHarvestDrops = null;

    @ZenProperty
    public Functions.Damage calcDamage = null;

    @ZenProperty
    public Functions.IsCriticalHit calcCrit = null;

    @ZenProperty
    public Functions.MiningSpeed getMiningSpeed = null;

    @ZenProperty
    public Functions.OnHit onHit = null;

    @ZenProperty
    public Functions.OnUpdate onUpdate = null;

    @ZenProperty
    public Functions.AfterHit afterHit = null;

    @ZenProperty
    public Functions.KnockBack calcKnockBack = null;

    @ZenProperty
    public Functions.OnBlock onBlock = null;

    @ZenProperty
    public Functions.OnToolDamage onToolDamage = null;

    @ZenProperty
    public Functions.OnToolHeal calcToolHeal = null;

    @ZenProperty
    public Functions.OnToolRepair onToolRepair = null;

    @ZenProperty
    public Functions.OnPlayerHurt onPlayerHurt = null;

    @ZenProperty
    public Functions.CanApplyTogetherTrait canApplyTogetherTrait = null;

    @ZenProperty
    public Functions.CanApplyTogetherEnchantment canApplyTogetherEnchantment = null;

    @ZenProperty
    public Functions.ExtraInfo extraInfo = null;

    @ZenProperty
    public String localizedName = null;

    @ZenProperty
    public String localizedDescription = null;

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
        trait.canApplyTogetherTrait = this.canApplyTogetherTrait;
        trait.canApplyTogetherEnchantment = this.canApplyTogetherEnchantment;
        trait.extraInfo = this.extraInfo;
        trait.localizedName = this.localizedName;
        trait.localizedDescription = this.localizedDescription;

        for (CoTRecipeMatch recipeMatch : recipeMatches) {
            trait.addItem(recipeMatch);
        }

        TinkerRegistry.addTrait(trait);

        return new TConTraitRepresentation(trait);
    }
}

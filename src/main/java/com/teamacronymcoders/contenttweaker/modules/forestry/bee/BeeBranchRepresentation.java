package com.teamacronymcoders.contenttweaker.modules.forestry.bee;

import crafttweaker.CraftTweakerAPI;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.alleles.AlleleEffects;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMemberSetter;

@ZenClass("mods.contenttweaker.forestry.BeeBranch")
public class BeeBranchRepresentation {
    public final String name;
    private IAllele[] traits;

    public BeeBranchRepresentation(String name) {
        this.name = name;
    }

    @ZenMemberSetter
    public void setTrait(String name, Object value) {
        StringBuilder enumNameBuilder = new StringBuilder();
        for (char character : name.toCharArray()) {
            if (Character.isUpperCase(character)) {
                enumNameBuilder.append("_");
                enumNameBuilder.append(Character.toLowerCase(character));
            } else {
                enumNameBuilder.append(character);
            }

        }
        EnumBeeChromosome chromosome = getBeeChromosome(enumNameBuilder.toString());
        if (chromosome != null) {

        } else {
            CraftTweakerAPI.logError("Failed to Find Chromosome For");
        }
    }

    private EnumBeeChromosome getBeeChromosome(String enumName) {
        for (EnumBeeChromosome beeChromosome: EnumBeeChromosome.values()) {
            if (beeChromosome.getName().equalsIgnoreCase(enumName)) {
                return beeChromosome;
            }
        }
        return null;
    }

    private IAllele[] generateDefaults() {
        IAllele[] defaultTemplate = new IAllele[EnumBeeChromosome.values().length];
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.NEVER_SLEEPS, false);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TOLERATES_RAIN, false);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.CAVE_DWELLING, false);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
        AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.EFFECT, AlleleEffects.effectNone);
        return defaultTemplate;
    }
}

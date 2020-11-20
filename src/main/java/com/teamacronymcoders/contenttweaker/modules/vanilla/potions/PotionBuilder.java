package com.teamacronymcoders.contenttweaker.modules.vanilla.potions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.attribute.IEntityAttribute;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import crafttweaker.api.potions.IPotion;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.HashMap;
import java.util.Map;

@ZenClass("mods.contenttweaker.potions.PotionBuilder")
@ZenRegister
public class PotionBuilder {

    @ZenProperty
    public String name;

    public PotionBuilder(String name) {
        this.name = name;
    }

    @ZenMethod
    public static PotionBuilder create(String name) {
        return new PotionBuilder(name);
    }

    /////////////////////////////

    @ZenProperty
    public boolean isBadEffect = false;

    @ZenProperty
    public boolean isInstant = false;

    @ZenProperty
    public boolean isBeneficial = true;

    @ZenProperty
    public int color;

    @ZenSetter("color")
    public void setColor(CTColor color) {
        this.color = color.getIntColor();
    }

    @ZenProperty
    public String domain = "contenttweaker";

    @ZenProperty
    public PotionFunctions.isReady isReady = null;

    @ZenProperty
    public PotionFunctions.performEffect performEffect  = null;

    @ZenProperty
    public PotionFunctions.affectEntity affectEntity = null;

    @ZenProperty
    public Map<IEntityAttribute, IEntityAttributeModifier> attributes = new HashMap<>();

    @ZenMethod
    public IPotion register() {
        CoTPotion potion = new CoTPotion(this.isBadEffect, this.color);

        potion.isInstant = this.isInstant;

        if (this.isBeneficial) {
            potion.setBeneficial();
        }

        potion.isReady = this.isReady;
        potion.performEffect = this.performEffect;
        potion.affectEntity = this.affectEntity;

        if (this.attributes != null && !this.attributes.isEmpty()) {
            for (Map.Entry<IEntityAttribute, IEntityAttributeModifier> entry : attributes.entrySet()) {
                IEntityAttributeModifier modifier = entry.getValue();
                potion.registerPotionAttributeModifier((IAttribute) entry.getKey().getInternal(), modifier.getUUID(), modifier.getAmount(), modifier.getOperation());
            }
        }

        //Registering
        potion.setPotionName(name);
        potion.setRegistryName(domain, name);
        ForgeRegistries.POTIONS.register(potion);

        return potion.thisDefinition;
    }
}
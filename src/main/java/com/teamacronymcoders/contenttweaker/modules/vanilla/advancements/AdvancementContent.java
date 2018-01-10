package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@ZenClass("mods.contenttweaker.Advancement")
public class AdvancementContent {

    @ZenProperty
    public Advancement parent;

    @ZenProperty
    public DisplayInfo display;

    @ZenProperty
    public AdvancementRewards rewards;

    @ZenProperty
    public ResourceLocation id;

    @ZenProperty
    public Map<String, Criterion> criteria;

    @ZenProperty
    public String[][] requirements;

    @ZenProperty
    public Set<Advancement> children = Sets.newLinkedHashSet();

    @ZenProperty
    public ITextComponent displayText;


    public addAdvancement(ResourceLocation id, @Nullable Advancement parentIn, @Nullable DisplayInfo displayIn, AdvancementRewards rewardsIn, Map<String, Criterion> criteriaIn, String[][] requirementsIn){
        this.id = id;
        this.display = displayIn;
        this.criteria = ImmutableMap.copyOf(criteriaIn);
        this.parent = parentIn;
        this.rewards = rewardsIn;
        this.requirements = requirementsIn;

        if (parentIn != null) {
            parentIn.addChild(this.);
        }

        if (displayIn == null) {
            this.displayText = new TextComponentString(id.toString());
        }
        else {
            this.displayText = new TextComponentString("[");
            this.displayText.getStyle().setColor(displayIn.getFrame().getFormat());
            ITextComponent itextcomponent = displayIn.getTitle().createCopy();
            ITextComponent itextcomponent1 = new TextComponentString("");
            ITextComponent itextcomponent2 = itextcomponent.createCopy();
            itextcomponent2.getStyle().setColor(displayIn.getFrame().getFormat());
            itextcomponent1.appendSibling(itextcomponent2);
            itextcomponent1.appendText("\n");
            itextcomponent1.appendSibling(displayIn.getDescription());
            itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, itextcomponent1));
            this.displayText.appendSibling(itextcomponent);
            this.displayText.appendText("]");
        }
    }

    @ZenMethod
    public Advancement.Builder copy() {
        return new Advancement.Builder(parent == null ? null : parent.getId(), display, rewards, criteria, requirements);
    }

    @ZenMethod
    public Advancement getParent() {
        return parent;
    }

    @ZenMethod
    public DisplayInfo getDisplay() {
        return this.display;
    }

    @ZenMethod
    public AdvancementRewards getRewards() {
        return this.rewards;
    }

    @ZenMethod
    public String toString(){
        return "SimpleAdvancement{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + '}';
    }

    @ZenMethod
    public Iterable<Advancement> getChildren(){
        return children;
    }

    @ZenMethod
    Map<String, Criterion> getCriteria(){
        return criteria;
    }

    @ZenMethod
    public int getRequirementCount() {
        return this.requirements.length;
    }

    @ZenMethod
    public void addChild(Advancement advancementIn){
        children.add(advancementIn);
    }

    public ResourceLocation getId(){
        return id;
    }

    public String[][] getRequirements() {
        return requirements;
    }

    public ITextComponent getDisplayText() {
        return displayText;
    }
}

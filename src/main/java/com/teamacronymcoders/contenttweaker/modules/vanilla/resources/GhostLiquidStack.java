package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.brackets.BracketHandlerLiquid;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class GhostLiquidStack implements ILiquidStack {

    public static final FluidStack FAKE_STACK = new FluidStack(FluidRegistry.WATER, 1);
    public static final ILiquidStack FAKE_LSTACK = new MCLiquidStack(FAKE_STACK);
    private static boolean warned = false;

    private final String name;
    private ILiquidStack liquid = FAKE_LSTACK;

    public GhostLiquidStack(String name) {

        this.name = name;
        if (!warned) {
            CraftTweakerAPI.logInfo("Liquid <liquid:" + name + ">" + " has not been found, trying to use a ghost representative. This message will only be printed once, all subsequent missing fluids will be handled the same way.");
            warned = true;
        }
        update();
    }

    public void update() {
        if (!FAKE_LSTACK.matches(liquid)) {
            return;
        }
        ILiquidStack stack = BracketHandlerLiquid.getLiquid(name);
        if (stack == null) {
            return;
        }
        stack = stack.withAmount(liquid.getAmount());
        if (liquid.getTag() != null) {
            stack = stack.withTag(liquid.getTag());
        }
        this.liquid = stack;

    }


    @Override
    public ILiquidDefinition getDefinition() {
        update();
        return liquid.getDefinition();
    }

    @Override
    public String getName() {
        update();
        return liquid.getName();
    }

    @Override
    public String getDisplayName() {
        update();
        return liquid.getDisplayName();
    }

    @Override
    public String getMark() {
        update();
        return liquid.getMark();
    }

    @Override
    public int getAmount() {
        update();
        return liquid.getAmount();
    }

    @Override
    public List<IItemStack> getItems() {
        update();
        return liquid.getItems();
    }

    @Override
    public IItemStack[] getItemArray() {
        update();
        return liquid.getItemArray();
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        update();
        return liquid.getLiquids();
    }

    @Override
    public IIngredient amount(int amount) {
        update();
        return liquid.amount(amount);
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        update();
        return liquid.or(ingredient);
    }

    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        update();
        return liquid.transformNew(transformer);
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        update();
        return liquid.only(condition);
    }

    @Override
    public IIngredient marked(String mark) {
        update();
        return liquid.marked(mark);
    }

    @Override
    public boolean matches(IItemStack item) {
        update();
        return liquid.matches(item);
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        update();
        return liquid.matchesExact(item);
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        update();
        return liquid.matches(liquid);
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        update();
        return liquid.contains(ingredient);
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        update();
        return liquid.applyTransform(item, byPlayer);
    }

    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        update();
        return liquid.applyNewTransform(item);
    }

    @Override
    public boolean hasNewTransformers() {
        update();
        return liquid.hasNewTransformers();
    }

    @Override
    public boolean hasTransformers() {
        update();
        return liquid.hasTransformers();
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        update();
        return liquid.transform(transformer);
    }

    @Override
    public int getLuminosity() {
        update();
        return liquid.getLuminosity();
    }

    @Override
    public int getDensity() {
        update();
        return liquid.getDensity();
    }

    @Override
    public int getTemperature() {
        update();
        return liquid.getTemperature();
    }

    @Override
    public int getViscosity() {
        update();
        return liquid.getViscosity();
    }

    @Override
    public boolean isGaseous() {
        update();
        return liquid.isGaseous();
    }

    @Override
    public IData getTag() {
        update();
        return liquid.getTag();
    }

    @Override
    public ILiquidStack withTag(IData data) {
        update();
        this.liquid = liquid.withTag(data);
        return this;
    }

    @Override
    public ILiquidStack withAmount(int amount) {
        update();
        this.liquid = liquid.withAmount(amount);
        return this;
    }

    @Override
    public Object getInternal() {
        update();
        if (liquid.getDefinition().getInternal().equals(FAKE_STACK.getFluid())) {
            CraftTweakerAPI.logError("Trying to access Ghost liquid before its ready: <liquid:" + name + ">");
        }
        return liquid.getInternal();
    }

    @Override
    public String toCommandString() {
        update();
        return liquid.toCommandString();
    }
}

package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Represents an item that is not yet existent.
 */
public class GhostItemStack implements IItemStack {

    public static final ItemStack FAKE_STACK = new ItemStack(new Item(), 1);
    public static final IItemStack FAKE_ISTACK = new MCItemStack(FAKE_STACK);
    private static boolean warned = false;
    private final String name;
    private final int meta;
    private IItemStack item = FAKE_ISTACK;

    public GhostItemStack(String name, int meta) {
        this.name = name;
        this.meta = meta;

        if (!warned) {
            CraftTweakerAPI.logInfo("Item <item:" + name + ":" + meta + "> has not been found, trying to use a ghost representative. This message will only be printed once, all subsequent missing items will be handled the same way.");
            warned = true;
        }

        update();

    }

    public void update() {
        if (!FAKE_ISTACK.matches(item)) {
            return;
        }
        IItemStack stack = BracketHandlerItem.getItem(name, meta);
        if (stack == null) {
            return;
        }
        stack = stack.withAmount(item.getAmount());
        stack = stack.withDamage(item.getDamage());

        if (item.hasTag()) {
            stack = stack.withTag(item.getTag());
        }

        this.item = stack;
    }

    public boolean isPresent() {
        update();
        return !FAKE_ISTACK.matches(item);
    }

    public IItemStack getItem() {
        update();
        return item;
    }

    @Override
    public IItemDefinition getDefinition() {
        update();
        return item.getDefinition();
    }

    @Override
    public String getName() {
        update();
        return item.getName();
    }

    @Override
    public String getDisplayName() {
        update();
        return item.getDisplayName();
    }

    @Override
    public void setDisplayName(String name) {
        update();
        item.setDisplayName(name);
    }

    @Override
    public int getMaxStackSize() {
        update();
        return item.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int size) {
        update();
        item.setMaxStackSize(size);
    }

    @Override
    public float getBlockHardness() {
        update();
        return item.getBlockHardness();
    }

    @Override
    public void setBlockHardness(float hardness) {
        update();
        item.setBlockHardness(hardness);
    }

    @Override
    public int getDamage() {
        update();
        return item.getDamage();
    }

    @Override
    public int getMaxDamage() {
        update();
        return item.getMaxDamage();
    }

    @Override
    public void setMaxDamage(int damage) {
        update();
        item.setMaxDamage(damage);
    }

    @Override
    public IData getTag() {
        update();
        return item.getTag();
    }

    @Override
    public ILiquidStack getLiquid() {
        update();
        return item.getLiquid();
    }

    @Override
    public String getMark() {
        update();
        return item.getMark();
    }

    @Override
    public int getAmount() {
        update();
        return item.getAmount();
    }

    @Override
    public List<IItemStack> getItems() {
        update();
        return item.getItems();
    }

    @Override
    public IItemStack[] getItemArray() {
        update();
        return item.getItemArray();
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        update();
        return item.getLiquids();
    }

    @Override
    public IItemStack amount(int amount) {
        update();
        this.item = item.amount(amount);
        return this;
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        update();
        return item.or(ingredient);
    }

    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        update();
        return item.transformNew(transformer);
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        update();
        return item.only(condition);
    }

    @Override
    public IIngredient marked(String mark) {
        update();
        return item.marked(mark);
    }

    @Override
    public WeightedItemStack percent(float p) {
        update();
        return item.percent(p);
    }

    @Override
    public WeightedItemStack weight(float p) {
        update();
        return item.weight(p);
    }

    @Override
    public IIngredient anyDamage() {
        update();
        return item.anyDamage();
    }

    @Override
    public IItemStack withDamage(int damage) {
        update();
        this.item = item.withDamage(damage);
        return this;
    }

    @Override
    public IItemStack withAmount(int amount) {
        update();
        this.item = item.withAmount(amount);
        return this;
    }

    @Override
    public IItemStack anyAmount() {
        update();
        this.item = item.anyAmount();
        return this;
    }

    @Override
    public IItemStack withTag(IData tag) {
        update();
        this.item = item.withTag(tag);
        return this;
    }

    @Override
    public IItemStack withEmptyTag() {
        update();
        this.item = item.withEmptyTag();
        return this;
    }

    @Override
    public IItemStack removeTag(String tag) {
        update();
        this.item = item.removeTag(tag);
        return this;
    }

    @Override
    public IItemStack updateTag(IData tagUpdate) {
        update();
        this.item = item.updateTag(tagUpdate);
        return this;
    }

    @Override
    public IBlock asBlock() {
        update();
        return item.asBlock();
    }

    @Override
    public List<IOreDictEntry> getOres() {
        update();
        return item.getOres();
    }

    @Override
    public IItemStack withDisplayName(String name) {
        update();
        this.item = item.withDisplayName(name);
        return this;
    }

    @Override
    public IItemStack withLore(String[] lore) {
        update();
        this.item = item.withLore(lore);
        return this;
    }

    @Override
    public List<String> getToolClasses() {
        update();
        return item.getToolClasses();
    }

    @Override
    public int getItemEnchantability() {
        update();
        return item.getItemEnchantability();
    }

    @Override
    public IItemStack getContainerItem() {
        update();
        this.item = item.getContainerItem();
        return this;
    }

    @Override
    public boolean isBeaconPayment() {
        update();
        return item.isBeaconPayment();
    }

    @Override
    public boolean canPlaceOn(IBlockDefinition block) {
        update();
        return item.canPlaceOn(block);
    }

    @Override
    public boolean canDestroy(IBlockDefinition block) {
        update();
        return item.canDestroy(block);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block) {
        update();
        return item.canHarvestBlock(block);
    }

    @Override
    public int getRepairCost() {
        update();
        return item.getRepairCost();
    }

    @Override
    public void setRepairCost(int repairCost) {
        update();
        item.setRepairCost(repairCost);
    }

    @Override
    public boolean canEditBlocks() {
        update();
        return item.canEditBlocks();
    }

    @Override
    public boolean isOnItemFrame() {
        update();
        return item.isOnItemFrame();
    }

    @Override
    public boolean isItemEnchanted() {
        update();
        return item.isItemEnchanted();
    }

    @Override
    public boolean isItemDamaged() {
        update();
        return item.isItemDamaged();
    }

    @Override
    public boolean isDamageable() {
        update();
        return item.isDamageable();
    }

    @Override
    public boolean isStackable() {
        update();
        return item.isStackable();
    }

    @Override
    public void addEnchantment(IEnchantment enchantment) {
        update();
        item.addEnchantment(enchantment);
    }

    @Override
    public boolean canApplyAtEnchantingTable(IEnchantmentDefinition enchantment) {
        update();
        return item.canApplyAtEnchantingTable(enchantment);
    }

    @Override
    public List<IEnchantment> getEnchantments() {
        update();
        return item.getEnchantments();
    }

    @Override
    public boolean isItemEnchantable() {
        update();
        return item.isItemEnchantable();
    }

    @Override
    public boolean hasEffect() {
        update();
        return item.hasEffect();
    }

    @Override
    public boolean hasDisplayName() {
        update();
        return item.hasDisplayName();
    }

    @Override
    public void clearCustomName() {
        update();

    }

    @Override
    public boolean hasTag() {
        update();
        return item.hasTag();
    }

    @Override
    public void damageItem(int amount, IEntity entity) {
        update();
        item.damageItem(amount, entity);
    }

    @Override
    public int getMetadata() {
        update();
        return item.getMetadata();
    }

    @Override
    public boolean getHasSubtypes() {
        update();
        return item.getHasSubtypes();
    }

    @Override
    public float getStrengthAgainstBlock(IBlockState blockState) {
        update();
        return item.getStrengthAgainstBlock(blockState);
    }

    @Override
    public IItemStack splitStack(int amount) {
        update();
        this.item = item.splitStack(amount);
        return this;
    }

    @Override
    public boolean isEmpty() {
        update();
        return item.isEmpty();
    }

    @Override
    public int getItemBurnTime() {
        update();
        return item.getItemBurnTime();
    }

    @Override
    public boolean showsDurabilityBar() {
        update();
        return item.showsDurabilityBar();
    }

    @Override
    public boolean hasCustomEntity() {
        update();
        return item.hasCustomEntity();
    }

    @Override
    public boolean hasContainerItem() {
        update();
        return item.hasContainerItem();
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, int x, int y, int z) {
        update();
        return item.createEntityItem(world, x, y, z);
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, IBlockPos pos) {
        update();
        return item.createEntityItem(world, pos);
    }

    @Override
    public boolean matches(IItemStack item) {
        update();
        return this.item.matches(item);
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        update();
        return this.item.matchesExact(item);
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        update();
        return item.matches(liquid);
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        update();
        return item.contains(ingredient);
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        update();
        this.item = item.applyTransform(item, byPlayer);
        return this;
    }

    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        update();
        this.item = item.applyNewTransform(item);
        return this;
    }

    @Override
    public boolean hasNewTransformers() {
        update();
        return item.hasNewTransformers();
    }

    @Override
    public boolean hasTransformers() {
        update();
        return item.hasTransformers();
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        update();
        return item.transform(transformer);
    }

    @Override
    public Object getInternal() {
        update();
        if (FAKE_ISTACK.matches(item)) {
            CraftTweakerAPI.logError("Trying to access Ghost item before it's ready: <item:" + name + ":" + meta + ">");
        }
        return item.getInternal();
    }

    @Override
    public String toCommandString() {
        update();
        return item.toCommandString();
    }
}

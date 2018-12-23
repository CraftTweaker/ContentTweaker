package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.data.DataString;
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
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collections;
import java.util.List;

/**
 * Represents an item that is not yet existent.
 */
public class GhostItemStack implements IItemStack {

    public static final ItemStack FAKE_STACK = new ItemStack(new Item(), 1);
    public static final IItemStack FAKE_ISTACK = new MCItemStack(FAKE_STACK);

    private static final int ANY_AMOUNT = -300;
    private static final int UNCHANGED = -600;

    private static boolean warned = false;

    private final String name;
    private final int meta;

    private int amount = UNCHANGED;
    private IData tag = null;
    private String mark;

    private IItemStack item = FAKE_ISTACK;
    private int damage = UNCHANGED;

    public GhostItemStack(String name, int meta) {
        this.name = name;
        this.meta = meta;

        if (!warned) {
            CraftTweakerAPI.logInfo("Item <item:" + name + ":" + meta + "> has not been found, trying to use a ghost representative. This message will only be printed once, all subsequent missing items will be handled the same way.");
            warned = true;
        }

        update();

    }

    public boolean update() {
        if (FAKE_ISTACK != item) {
            return true;
        }
        IItemStack stack = BracketHandlerItem.getItem(name, meta);
        if (stack == null) {
            return false;
        }

        if (amount == ANY_AMOUNT) {
            stack = stack.anyAmount();
        } else if (amount != UNCHANGED) {
            stack = stack.withAmount(amount);
        }
        if (damage != UNCHANGED) {
            stack = stack.withDamage(damage);
        }

        if (tag != null) {
            stack = stack.withTag(tag);
        }

        this.item = stack;
        return true;
    }

    @Override
    public IItemDefinition getDefinition() {
        if (update()) {
            return item.getDefinition();
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public String getName() {
        //Not the same for GIS, so it should be noticeable for packdevs
        return update() ? item.getName() : name;
    }

    @Override
    public String getDisplayName() {
        //Not the same for GIS, so it should be noticeable for packdevs
        return update() ? item.getName() : name;
    }

    @Override
    public void setDisplayName(String name) {
        if (update()) {
            item.setDisplayName(name);
        }
    }

    @Override
    public int getMaxStackSize() {
        return update() ? item.getMaxStackSize() : 0;
    }

    @Override
    public void setMaxStackSize(int size) {
        if (update()) {
            item.setMaxStackSize(size);
        }
    }

    @Override
    public float getBlockHardness() {
        return update() ? item.getBlockHardness() : 0;
    }

    @Override
    public void setBlockHardness(float hardness) {
        if (update()) {
            item.setBlockHardness(hardness);
        }
    }

    @Override
    public int getDamage() {
        return update() ? item.getDamage() : damage;
    }

    @Override
    public int getMaxDamage() {
        return update() ? item.getMaxDamage() : 0;
    }

    @Override
    public void setMaxDamage(int damage) {
        if (update()) {
            item.setMaxDamage(damage);
        }
    }

    @Override
    public IData getTag() {
        return update() ? item.getTag() : tag;
    }

    @Override
    public ILiquidStack getLiquid() {
        return update() ? item.getLiquid() : null;
    }

    @Override
    public String getMark() {
        return update() ? item.getMark() : mark;
    }

    @Override
    public int getAmount() {
        return update() ? item.getAmount() : amount;
    }

    @Override
    public List<IItemStack> getItems() {
        return update() ? item.getItems() : Collections.emptyList();
    }

    @Override
    public IItemStack[] getItemArray() {
        return update() ? item.getItemArray() : new IItemStack[0];
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        return update() ? item.getLiquids() : Collections.EMPTY_LIST;
    }

    @Override
    public IItemStack amount(int amount) {
        if (update()) {
            return item.amount(amount);
        }
        this.amount = amount;
        return this;
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        if (update()) {
            return item.or(ingredient);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        if (update()) {
            return item.transformNew(transformer);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        if (update()) {
            return item.only(condition);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IIngredient marked(String mark) {
        if (update()) {
            return item.marked(mark);
        }
        this.mark = mark;
        return this;
    }

    @Override
    public WeightedItemStack percent(float p) {
        if (update()) {
            return item.percent(p);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public WeightedItemStack weight(float p) {
        if (update()) {
            return item.weight(p);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IIngredient anyDamage() {
        if (update()) {
            return item.anyDamage();
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IItemStack withDamage(int damage) {
        if (update()) {
            return item.withDamage(damage);
        }
        this.damage = damage;
        return this;
    }

    @Override
    public IItemStack withAmount(int amount) {
        if (update()) {
            return item.withAmount(amount);
        }
        this.amount = amount;
        return this;
    }

    @Override
    public IItemStack anyAmount() {
        if (update()) {
            return item.anyAmount();
        }
        this.amount = ANY_AMOUNT;
        return this;
    }

    @Override
    public IItemStack withTag(IData tag) {
        if (update()) {
            return item.withTag(tag, true);
        }
        return this;
    }

    @Override
    public IItemStack withTag(IData tag, boolean matchTagExact) {
        if (update()) {
            return item.withTag(tag, matchTagExact);
        }
        return this;
    }

    @Override
    public IItemStack withEmptyTag() {
        if (update()) {
            return item.withEmptyTag();
        }
        this.tag = NBTConverter.from(new NBTTagCompound(), true);
        return this;
    }

    @Override
    public IItemStack removeTag(String tag) {
        if (update()) {
            return item.removeTag(tag);
        }
        this.tag = this.tag.sub(new DataString(tag));
        return this;
    }

    @Override
    public IItemStack updateTag(IData tagUpdate) {
        if (update()) {
            return item.updateTag(tagUpdate);
        }
        this.tag = this.tag.update(tagUpdate);
        return this;
    }

    @Override
    public IItemStack updateTag(IData tagUpdate, boolean matchTagExact) {
        if (update()) {
            return item.updateTag(tagUpdate, matchTagExact);
        }
        this.tag = this.tag.update(tagUpdate);
        return this;
    }

    @Override
    public IBlock asBlock() {
        if (update()) {
            return item.asBlock();
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public List<IOreDictEntry> getOres() {
        return update() ? item.getOres() : Collections.EMPTY_LIST;
    }

    @Override
    public IItemStack withDisplayName(String name) {
        if (update()) {
            return item.withDisplayName(name);
        }
        return this;
    }

    @Override
    public IItemStack withLore(String[] lore) {
        if (update()) {
            return item.withLore(lore);
        }
        return this;
    }

    @Override
    public List<String> getToolClasses() {
        return update() ? item.getToolClasses() : Collections.EMPTY_LIST;
    }

    @Override
    public int getItemEnchantability() {
        return update() ? item.getItemEnchantability() : 0;
    }

    @Override
    public IItemStack getContainerItem() {
        if (update()) {
            return item.getContainerItem();
        }
        return this;
    }

    @Override
    public boolean isBeaconPayment() {
        return update() && item.isBeaconPayment();
    }

    @Override
    public boolean canPlaceOn(IBlockDefinition block) {
        return update() && item.canPlaceOn(block);
    }

    @Override
    public boolean canDestroy(IBlockDefinition block) {
        return update() && item.canDestroy(block);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block) {
        return update() && item.canHarvestBlock(block);
    }

    @Override
    public int getRepairCost() {
        return update() ? item.getRepairCost() : 0;
    }

    @Override
    public void setRepairCost(int repairCost) {
        if (update()) {
            item.setRepairCost(repairCost);
        }
    }

    @Override
    public boolean canEditBlocks() {
        return update() && item.canEditBlocks();
    }

    @Override
    public boolean isOnItemFrame() {
        return update() && item.isOnItemFrame();
    }

    @Override
    public boolean isItemEnchanted() {
        return update() && item.isItemEnchanted();
    }

    @Override
    public boolean isItemDamaged() {
        return update() && item.isItemDamaged();
    }

    @Override
    public boolean isDamageable() {
        return update() && item.isDamageable();
    }

    @Override
    public boolean isStackable() {
        return update() && item.isStackable();
    }

    @Override
    public void addEnchantment(IEnchantment enchantment) {
        if (update()) {
            item.addEnchantment(enchantment);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(IEnchantmentDefinition enchantment) {
        return update() && item.canApplyAtEnchantingTable(enchantment);
    }

    @Override
    public List<IEnchantment> getEnchantments() {
        return update() ? item.getEnchantments() : Collections.EMPTY_LIST;
    }

    @Override
    public boolean isItemEnchantable() {
        return update() && item.isItemEnchantable();
    }

    @Override
    public boolean hasEffect() {
        return update() && item.hasEffect();
    }

    @Override
    public boolean hasDisplayName() {
        return update() && item.hasDisplayName();
    }

    @Override
    public void clearCustomName() {
        if (update()) {
            item.clearCustomName();
        }
    }

    @Override
    public boolean hasTag() {
        return update() ? item.hasTag() : tag != null;
    }

    @Override
    public void damageItem(int amount, IEntity entity) {
        if (update()) {
            item.damageItem(amount, entity);
        }
    }

    @Override
    public int getMetadata() {
        return update() ? item.getMetadata() : meta;
    }

    @Override
    public boolean getHasSubtypes() {
        return update() && item.getHasSubtypes();
    }

    @Override
    public float getStrengthAgainstBlock(IBlockState blockState) {
        return update() ? item.getStrengthAgainstBlock(blockState) : 0f;
    }

    @Override
    public IItemStack splitStack(int amount) {
        return update() ? item.splitStack(amount) : this;
    }

    @Override
    public boolean isEmpty() {
        return !update() || item.isEmpty();
    }

    @Override
    public int getItemBurnTime() {
        return update() ? item.getItemBurnTime() : 0;
    }

    @Override
    public boolean showsDurabilityBar() {
        return update() && item.showsDurabilityBar();
    }

    @Override
    public boolean hasCustomEntity() {
        return update() && item.hasCustomEntity();
    }

    @Override
    public boolean hasContainerItem() {
        return update() && item.hasContainerItem();
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, int x, int y, int z) {
        if (update()) {
            return item.createEntityItem(world, x, y, z);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, IBlockPos pos) {
        if (update()) {
            return item.createEntityItem(world, pos);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public boolean matches(IItemStack item) {
        return update() && this.item.matches(item);
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        return update() && this.item.matchesExact(item);
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        return update() && this.item.matches(liquid);
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        return update() && this.item.contains(ingredient);
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        if (update()) {
            return item.applyTransform(item, byPlayer);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        if (update()) {
            return item.applyNewTransform(item);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public boolean hasNewTransformers() {
        return update() && this.item.hasNewTransformers();
    }

    @Override
    public boolean hasTransformers() {
        return update() && this.item.hasTransformers();
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        if (update()) {
            return item.transform(transformer);
        }
        throw new IllegalStateException("GhostItemStack not yet initialized!");
    }

    @Override
    public Object getInternal() {
        if (!update()) {
            CraftTweakerAPI.logError("Trying to access Ghost item before it's ready: <item:" + name + ":" + meta + ">");
        }
        return item.getInternal();
    }

    @Override
    public String toCommandString() {
        return update() ? item.toCommandString() : "(GhostItemStack) <item:" + name + ":" + meta + ">";
    }
}

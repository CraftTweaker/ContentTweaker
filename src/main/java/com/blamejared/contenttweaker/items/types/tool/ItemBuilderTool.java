package com.blamejared.contenttweaker.items.types.tool;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

/**
 * A special builder that allows you to create items that can be used as tools.
 * You should have set the item's max damage before changing to this builder.
 * <p>
 * Has special methods that allow you to set the mining level for several tool types as well as the attack damage.
 *
 * @docParam this new ItemBuilder().withMaxDamage(150).withType<ItemBuilderTool>()
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.tool.ItemBuilderTool")
@Document("mods/contenttweaker/API/item/tool/ItemBuilderTool")
public class ItemBuilderTool extends ItemTypeBuilder {
    
    private final Map<ToolType, Float> miningSpeeds;
    private double attackSpeed;
    private double attackDamage;
    private int durabilityCostAttack;
    private int durabilityCostMining;
    
    public ItemBuilderTool(ItemBuilder builder) {
        super(builder);
        this.miningSpeeds = new HashMap<>();
        this.durabilityCostAttack = 2;
        this.durabilityCostMining = 1;
    }
    
    Map<ToolType, Float> getMiningSpeeds() {
        return miningSpeeds;
    }
    
    /**
     * Allows you to add a tool type to this tool.
     * You can specify the type, the mining level and optionally the mining speed when this type is hit as well.
     *
     * @param toolType    The type of the tool
     * @param miningLevel The mining level for this tool type
     * @param miningSpeed How fast this tool can mine blocks of the given type
     * @return This builder, used for method chaining
     * @docParam toolType <tooltype:shovel>
     * @docParam miningLevel 3
     * @docParam miningSpeed 2.0f
     */
    @ZenCodeType.Method
    public ItemBuilderTool withToolType(MCToolType toolType, int miningLevel, @ZenCodeType.OptionalFloat(1.0F) float miningSpeed) {
        itemBuilder.getItemProperties().addToolType(toolType.getInternal(), miningLevel);
        miningSpeeds.put(toolType.getInternal(), miningSpeed);
        return this;
    }
    
    /**
     * Allows you to set the attack damage bonus that you get when holding this item
     *
     * @param attackDamage The additional attack damage
     * @return This builder, used for method chaining
     * @docParam attackDamage 2.0f
     */
    @ZenCodeType.Method
    public ItemBuilderTool withAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }
    
    /**
     * Allows you to set the attack speed bonus that you get when holding this item.
     *
     * @param attackSpeed The attack speed
     * @return This builder, used for method chaining.
     * @docParam attackSpeed 2.0d
     */
    @ZenCodeType.Method
    public ItemBuilderTool withAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }
    
    /**
     * Allows you to set the amount of damage that this item will receive when hitting enemies.
     * By default this is `0`
     *
     * @param durabilityCostAttack The damage points this item will receive
     * @return This builder, used for method chaining
     * @docParam durabilityCostAttack 5
     */
    @ZenCodeType.Method
    public ItemBuilderTool withDurabilityCostAttack(int durabilityCostAttack) {
        this.durabilityCostAttack = durabilityCostAttack;
        return this;
    }
    
    /**
     * Allows you to set the amount of damage that this item will receive when mining blocks.
     * By default this is `0`
     *
     * @param durabilityCostMining The damage points this item will receive
     * @return This builder, used for method chaining
     * @docParam durabilityCostMining 1
     */
    @ZenCodeType.Method
    public ItemBuilderTool withDurabilityCostMining(int durabilityCostMining) {
        this.durabilityCostMining = durabilityCostMining;
        return this;
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTItemTool itemTool = new CoTItemTool(this, location);
        VanillaFactory.queueItemForRegistration(itemTool);
    }
    
    public double getAttackSpeed() {
        return attackSpeed;
    }
    
    public double getAttackDamage() {
        return attackDamage;
    }
    
    public int getDurabilityCostAttack() {
        return durabilityCostAttack;
    }
    
    public int getDurabilityCostMining() {
        return durabilityCostMining;
    }
}

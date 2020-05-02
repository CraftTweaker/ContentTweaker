package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.contenttweaker.items.functions.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.food.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

/**
 * This class is used to create new Items to the game.
 * Absolutely must be run during `#loader contenttweaker` !
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemProperties")
@Document("mods/contenttweaker/item/MCItemProperties")
public class MCItemProperties {
    
    private final Map<ToolType, ToolTypeFunction> toolClasses = new HashMap<>();
    @ZenCodeType.Field
    public MCItemGroup itemGroup = new MCItemGroup(ItemGroup.SEARCH);
    @ZenCodeType.Field
    public int maxStackSize = 64;
    @ZenCodeType.Field
    public int maxDamage = 0;
    @ZenCodeType.Field
    public MCFood food;
    @ZenCodeType.Field
    public boolean canRepair = true;
    @ZenCodeType.Field
    public ToolDestroySpeedFunction destroySpeedFunction = (stack, state) -> 1.0f;
    
    public FunctionResourceLocationToIData nameToModel;
    
    /**
     * The starting point, creates a Properties object with default values
     */
    @ZenCodeType.Constructor
    public MCItemProperties() {
        nameToModel = name -> {
            final Map<String, IData> content = new HashMap<>(2);
            content.put("parent", new StringData("item/generated"));
            final StringData value = new StringData(new ResourceLocation(name.getNamespace(), "item/" + name
                    .getPath()).toString());
            content.put("textures", new MapData(Collections.singletonMap("layer0", value)));
            return new MapData(content);
        };
    }
    
    public Map<ToolType, ToolTypeFunction> getToolClasses() {
        return toolClasses;
    }
    
    /**
     * Sets the max stack size.
     * <p>
     * Conflicts with withMaxDamage since maxDamage will only work if the stack size is 1
     * By default 64, unless withMaxDamage has been called, then 1
     *
     * @param maxStackSize The maximum Stacksize of the object
     * @return This object for chaining
     * @docParam maxStackSize 16
     */
    @ZenCodeType.Method
    public MCItemProperties withMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    /**
     * Sets the max damage. Cannot be used together with {@link #withMaxStackSize(int)}.
     * Always sets the max Stack size to 1
     * By default 0, which means the item has infinite uses
     *
     * @param maxDamage The maximum damage of the object
     * @return This object for chaining
     * @docParam maxDamage 60
     */
    @ZenCodeType.Method
    public MCItemProperties withMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }
    
    /**
     * Enables this item to be used as food
     * By default an item has no food value
     *
     * @param food The food values
     * @return This object for chaining
     * @docParam food new MCFood(4, 4.0f)
     */
    @ZenCodeType.Method
    public MCItemProperties withFood(MCFood food) {
        this.food = food;
        return this;
    }
    
    /**
     * Sets if the item can be repaired or not.
     * True by default
     *
     * @param canRepair Can this item be repaired
     * @return This object for chaining
     * @docParam canRepair true
     */
    @ZenCodeType.Method
    public MCItemProperties withCanRepair(boolean canRepair) {
        this.canRepair = canRepair;
        return this;
    }
    
    /**
     * Sets the itemGroup for this item.
     * The item will always appear in the search group (= creative search), however.
     * <p>
     * By default `<itemgroup:misc>`
     *
     * @param itemGroup The group that this item should appear in.
     * @return This object for chaining
     * @docParam itemGroup <itemgroup:buildingBlocks>
     */
    @ZenCodeType.Method
    public MCItemProperties withItemGroup(MCItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }
    
    /**
     * Allows this item to be used as tool.
     * Can be used multiple times with different ToolTypes.
     * <p>
     * The provided function calculates the Harvest level against the given block.
     *
     * @param type The tooltype this item will be
     * @param fun  Calculates the Harvest level
     * @return This object for chaining.
     * @docParam type <tooltype:axe>
     * @docParam fun (stack, type, player, blockState) => 3
     */
    @ZenCodeType.Method
    public MCItemProperties isTool(MCToolType type, ToolTypeFunction fun) {
        toolClasses.put(type.getInternal(), fun);
        return this;
    }
    
    /**
     * Allows this item to be used as tool with the given harvestLevel
     * Can be used multiple times with different ToolTypes.
     *
     * @param type         The tooltype this item will be
     * @param harvestLevel The harvest level of the tool
     * @return This object for chaining
     * @docParam type <tooltype:axe>
     * @docParam harvestLevel 3
     */
    @ZenCodeType.Method
    public MCItemProperties isTool(MCToolType type, int harvestLevel) {
        return isTool(type, (stack, type1, player, blockState) -> harvestLevel);
    }
    
    /**
     * Sets how the item calculates how fast it can mine given blocks.
     *
     * @param fun The function that will be executed
     * @return This object for chaining
     * @docParam fun (stack, state) => state.harvestTool in [<tooltype:shovel>, <tooltype:axe>] ? 3 : 1
     */
    @ZenCodeType.Method
    public MCItemProperties withDestroySpeed(ToolDestroySpeedFunction fun) {
        destroySpeedFunction = fun;
        return this;
    }
    
    /**
     * Registers this item to the game.
     * If this method is not called, then all your other chagnes won't do anything
     *
     * @param name The resource path of this item.
     * @docParam name "my_generic_item"
     */
    @ZenCodeType.Method
    public void build(String name) {
        VanillaFactory.registerItem(new CoTItem(this), new MCResourceLocation(new ResourceLocation(ContentTweaker.MOD_ID, name)));
    }
    
    public Item.Properties createInternal() {
        Item.Properties properties = new Item.Properties().group(itemGroup.getInternal())
                .maxStackSize(maxStackSize);
        
        if(maxDamage != 0) {
            properties = properties.maxDamage(maxDamage);
        }
        
        if(!canRepair) {
            properties = properties.setNoRepair();
        }
        if(food != null) {
            properties = properties.food(food.getInternal());
        }
        return properties;
    }
}

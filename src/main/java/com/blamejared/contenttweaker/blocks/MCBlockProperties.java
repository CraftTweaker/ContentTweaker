package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.contenttweaker.blocks.presets.*;
import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCBlockProperties")
@Document("mods/contenttweaker/block/MCBlockProperties")
public class MCBlockProperties {
    
    private final Block.Properties internal;
    private final Map<MCBlockStateProperty, String> blockStatePropertyList;
    private final Set<MCBlockStateProperty> blockStatePropertiesForBlockStateJson;
    @ZenCodeType.Field
    public MCItemGroup itemGroup;
    @ZenCodeType.Field
    public int maxStackSize = 64;
    @ZenCodeType.Field
    @ZenCodeType.Nullable
    public FunctionResourceLocationToIData blockToBlockStateMapping;
    @ZenCodeType.Field
    @ZenCodeType.Nullable
    public FunctionResourceLocationToIData modelToModelContentMapping;
    @ZenCodeType.Field
    @ZenCodeType.Nullable
    public PlaceStateMapping placeStateMapping;
    
    @ZenCodeType.Field
    @ZenCodeType.Nullable
    public BlockShapeFunction blockShapeFunction;
    
    @ZenCodeType.Field
    @ZenCodeType.Nullable
    public Consumer<MCResourceLocation> blockRegisteredCallBack;
    
    @ZenCodeType.Constructor
    public MCBlockProperties(@ZenCodeType.Optional("<blockmaterial:iron>") MCBlockMaterial material) {
        this(Block.Properties.create(material.getInternal()));
    }
    
    public MCBlockProperties(Block.Properties internal) {
        this.internal = internal;
        this.blockStatePropertyList = new HashMap<>();
        this.itemGroup = new MCItemGroup(ItemGroup.SEARCH);
        this.withBlockStateToModelMapping((name, blockValues) -> new MapData(Collections.singletonMap("model", new StringData(name
                .getNamespace() + ":block/" + name.getPath()))));
        
        this.modelToModelContentMapping = name -> {
            final Map<String, IData> content = new HashMap<>();
            content.put("parent", new StringData("block/cube_all"));
            content.put("textures", new MapData(Collections.singletonMap("all", new StringData(name.toString()))));
            return new MapData(content);
        };
        blockStatePropertiesForBlockStateJson = new HashSet<>();
    }
    
    @ZenCodeType.Method
    public static MCBlockProperties copyFrom(MCBlock block) {
        return new MCBlockProperties(Block.Properties.from(block.getInternal()));
    }
    
    public Map<MCBlockStateProperty, String> getBlockStatePropertyMap() {
        return blockStatePropertyList;
    }
    
    public Set<MCBlockStateProperty> getBlockStatePropertiesForBlockStateJson() {
        return blockStatePropertiesForBlockStateJson;
    }
    
    public Block.Properties getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties doesNotBlockMovement() {
        Block.Properties set = internal.doesNotBlockMovement();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withSlipperiness(float slipperinessIn) {
        Block.Properties set = internal.slipperiness(slipperinessIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withSound(MCSoundType soundTypeIn) {
        Block.Properties set = internal.sound(soundTypeIn.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withLightValue(int lightValueIn) {
        Block.Properties set = internal.lightValue(lightValueIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHardnessAndResistance(float hardnessIn, float resistanceIn) {
        Block.Properties set = internal.hardnessAndResistance(hardnessIn, resistanceIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHardnessAndResistance(float hardnessAndResistance) {
        Block.Properties set = internal.hardnessAndResistance(hardnessAndResistance);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withTickRandomly() {
        Block.Properties set = internal.tickRandomly();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withVariableOpacity() {
        Block.Properties set = internal.variableOpacity();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHarvestLevel(int harvestLevel) {
        Block.Properties set = internal.harvestLevel(harvestLevel);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHarvestTool(MCToolType harvestTool) {
        Block.Properties set = internal.harvestTool(harvestTool.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withNoDrops() {
        Block.Properties set = internal.noDrops();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties applyLootFrom(MCBlock blockIn) {
        Block.Properties set = internal.lootFrom(blockIn.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withItemGroup(MCItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withBlockStateProperty(MCBlockStateProperty property, String defaultValue) {
        return withBlockStateProperty(property, defaultValue, true);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withBlockStateProperty(MCBlockStateProperty property, String defaultValue, boolean usedInBlockStateJson) {
        this.blockStatePropertyList.put(property, defaultValue);
        if(usedInBlockStateJson) {
            this.blockStatePropertiesForBlockStateJson.add(property);
        }
        return this;
    }
    
    /**
     * @param mapping The mapping, null to suppress automatic json creation for this block
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCBlockProperties withBlockStateToModelMapping(BlockStateToModelMapping mapping) {
        this.blockToBlockStateMapping = name -> HelpersThatNeedToBeRefactored.getAllValues(this, mapping, name);
        return this;
    }
    
    /**
     * @param mapping The mapping
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCBlockProperties withModelNameToModelContentMapping(FunctionResourceLocationToIData mapping) {
        this.modelToModelContentMapping = mapping;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withBlockPlaceStateMapper(PlaceStateMapping mapping) {
        this.placeStateMapping = mapping;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withBlockShapeFunction(BlockShapeFunction blockShapeFunction) {
        this.blockShapeFunction = blockShapeFunction;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withBlockRegisteredCallBack(Consumer<MCResourceLocation> blockRegisteredCallBack) {
        this.blockRegisteredCallBack = blockRegisteredCallBack;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withPreset(Preset preset) {
        return preset.apply(this);
    }
    
    @ZenCodeType.Method
    public void build(String name) {
        final MCResourceLocation resourceLocation = new MCResourceLocation(new ResourceLocation(ContentTweaker.MOD_ID, name));
        final MCItemProperties mcItemProperties = new MCItemProperties().withMaxStackSize(maxStackSize)
                .withItemGroup(itemGroup);
        final CoTBlock block = new CoTBlock(this) {
            @Override
            protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
                for(MCBlockStateProperty property : getBlockStatePropertyMap().keySet()) {
                    builder.add(property.getInternal());
                }
            }
        };
        VanillaFactory.registerBlock(block, mcItemProperties, resourceLocation);
        if(blockRegisteredCallBack != null) {
            blockRegisteredCallBack.accept(resourceLocation);
        }
    }
}

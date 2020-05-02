package com.blamejared.contenttweaker.blocks.presets.stairs;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.presets.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;
import java.util.function.*;

public class PresetStairs implements Preset {
    
    public static Set<MCResourceLocation> knownStairTypes = new HashSet<>();
    
    private final Function<MCResourceLocation, String> up;
    private final Function<MCResourceLocation, String> down;
    private final Function<MCResourceLocation, String> sides;
    
    public PresetStairs() {
        this(null, null, null);
    }
    
    public PresetStairs(MCResourceLocation up, MCResourceLocation down, MCResourceLocation sides) {
        this.up = getFunction(up, "up");
        this.down = getFunction(down, "down");
        this.sides = getFunction(sides, "side");
    }
    
    private static Function<MCResourceLocation, String> getFunction(MCResourceLocation inputValue, String suffix) {
        return resourceLocation -> {
            if(inputValue != null) {
                return inputValue.toString();
            }
            final String s = resourceLocation.toString();
            return (s.endsWith("_outer") || s.endsWith("_inner") ? s.substring(0, s.length() - 6) : s) + "_" + suffix;
        };
    }
    
    @Override
    public MCBlockProperties apply(MCBlockProperties properties) {
        return properties.withBlockStateProperty(BracketHandlers.getBlockStateProperty("horizontal_facing"), "north")
                .withBlockStateProperty(BracketHandlers.getBlockStateProperty("half"), "bottom")
                .withBlockStateProperty(BracketHandlers.getBlockStateProperty("stairs_shape"), "straight")
                .withBlockStateProperty(BracketHandlers.getBlockStateProperty("waterlogged"), "false", false)
                .withBlockStateToModelMapping((name, blockValues) -> {
                    final MapData out = new MapData();
                    {
                        int y;
                        if(blockValues.get("half").equals("bottom")) {
                            y = blockValues.get("shape").contains("left") ? 270 : 0;
                        } else {
                            out.put("x", new IntData(180));
                            y = blockValues.get("shape").contains("right") ? 90 : 0;
                        }
                        switch(blockValues.get("facing")) {
                            case "east":
                                break;
                            case "south":
                                y += 90;
                                break;
                            case "west":
                                y += 180;
                                break;
                            case "north":
                            default:
                                y += 270;
                                break;
                        }
                        
                        if(y % 360 != 0) {
                            out.put("y", new IntData(y % 360));
                            out.put("uvlock", new BoolData(true));
                        } else if(blockValues.get("half").equals("top")) {
                            out.put("uvlock", new BoolData(true));
                        }
                    }
                    {
                        final String location = name.getNamespace() + ":block/" + name.getPath();
                        final String shape = blockValues.get("shape");
                        if(shape.contains("outer")) {
                            out.put("model", new StringData(location + "_outer"));
                        } else if(shape.contains("inner")) {
                            out.put("model", new StringData(location + "_inner"));
                        } else {
                            out.put("model", new StringData(location));
                        }
                    }
                    return out;
                })
                .withModelNameToModelContentMapping(name -> {
                    final MapData out = new MapData();
                    {
                        final String path = name.getPath();
                        final String parent;
                        if(path.endsWith("_outer")) {
                            parent = "block/outer_stairs";
                        } else if(path.endsWith("_inner")) {
                            parent = "block/inner_stairs";
                        } else {
                            parent = "block/stairs";
                        }
                        out.put("parent", new StringData(parent));
                    }
                    
                    final MapData textures = new MapData();
                    textures.put("top", new StringData(this.up.apply(name)));
                    textures.put("bottom", new StringData(this.down.apply(name)));
                    textures.put("side", new StringData(this.sides.apply(name)));
                    out.put("textures", textures);
                    
                    return out;
                })
                .withBlockPlaceStateMapper((defaultState, mcContext) -> {
                    final BlockItemUseContext context = mcContext.getInternal();
                    final BlockPos blockpos = context.getPos();
                    final Direction direction = context.getFace();
                    final MCBlockState mcBlockState = defaultState.withProperty("facing", context.getPlacementHorizontalFacing()
                            .getName2())
                            .withProperty("half", direction != Direction.DOWN && (direction == Direction.UP || !(context
                                    .getHitVec().y - (double) blockpos.getY() > 0.5D)) ? "bottom" : "top")
                            .withProperty("waterlogged", Boolean.toString(context.getWorld()
                                    .getFluidState(blockpos)
                                    .getFluid() == Fluids.WATER));
                    return mcBlockState.withProperty("shape", StairHelpers.getShapeProperty(mcBlockState
                            .getInternal(), context.getWorld(), blockpos).getName());
                    
                })
                .withBlockShapeFunction(StairHelpers::getShape)
                .withBlockRegisteredCallBack(knownStairTypes::add);
        
    }
    
}

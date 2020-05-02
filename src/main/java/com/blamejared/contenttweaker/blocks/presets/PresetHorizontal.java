package com.blamejared.contenttweaker.blocks.presets;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

import java.util.function.*;

public class PresetHorizontal implements Preset {
    
    private final Function<MCResourceLocation, String> up;
    private final Function<MCResourceLocation, String> down;
    private final Function<MCResourceLocation, String> front;
    private final Function<MCResourceLocation, String> left;
    private final Function<MCResourceLocation, String> back;
    private final Function<MCResourceLocation, String> right;
    
    public PresetHorizontal(MCResourceLocation up, MCResourceLocation down, MCResourceLocation front, MCResourceLocation left, MCResourceLocation back, MCResourceLocation right) {
        this.up = getFunction(up, "up");
        this.down = getFunction(down, "down");
        this.front = getFunction(front, "front");
        this.left = getFunction(left, "left");
        this.back = getFunction(back, "back");
        this.right = getFunction(right, "right");
    }
    
    public PresetHorizontal(MCResourceLocation upDown, MCResourceLocation front, MCResourceLocation left, MCResourceLocation back, MCResourceLocation right) {
        this.up = getFunction(upDown, "up_down");
        this.down = this.up;
        this.front = getFunction(front, "front");
        this.left = getFunction(left, "left");
        this.right = getFunction(right, "right");
        this.back = getFunction(back, "back");
    }
    
    public PresetHorizontal(MCResourceLocation upDown, MCResourceLocation sides) {
        this.up = getFunction(upDown, "up_down");
        this.down = this.up;
        
        this.front = getFunction(sides, "sides");
        
        this.left = this.front;
        this.right = this.front;
        this.back = this.front;
    }
    
    public PresetHorizontal(MCResourceLocation up, MCResourceLocation down, MCResourceLocation sides) {
        this.up = getFunction(up, "up");
        this.down = getFunction(down, "down");
        this.front = getFunction(sides, "sides");
        this.left = this.front;
        this.right = this.front;
        this.back = this.front;
    }
    
    public PresetHorizontal() {
        this.up = resourceLocation -> resourceLocation.toString() + "_up";
        this.down = resourceLocation -> resourceLocation.toString() + "_down";
        this.left = resourceLocation -> resourceLocation.toString() + "_left";
        this.right = resourceLocation -> resourceLocation.toString() + "_right";
        this.front = resourceLocation -> resourceLocation.toString() + "_front";
        this.back = resourceLocation -> resourceLocation.toString() + "_back";
    }
    
    private static Function<MCResourceLocation, String> getFunction(MCResourceLocation inputValue, String suffix) {
        return resourceLocation -> inputValue == null ? resourceLocation.toString() + "_" + suffix : inputValue
                .toString();
    }
    
    @Override
    public MCBlockProperties apply(MCBlockProperties properties) {
        return properties.withBlockStateProperty(BracketHandlers.getBlockStateProperty("horizontal_facing"), "north")
                .withBlockStateToModelMapping((name, blockValues) -> {
                    final MapData out = new MapData();
                    final int y;
                    switch(blockValues.get("facing")) {
                        case "north":
                        default:
                            y = 0;
                            break;
                        case "east":
                            y = 90;
                            break;
                        case "south":
                            y = 180;
                            break;
                        case "west":
                            y = 270;
                    }
                    if(y != 0) {
                        out.put("y", new IntData(y));
                    }
                    
                    out.put("model", new StringData(name.getNamespace() + ":block/" + name.getPath()));
                    return out;
                })
                .withModelNameToModelContentMapping(name -> {
                    final MapData out = new MapData();
                    out.put("parent", new StringData("block/cube"));
                    final MapData display = new MapData();
                    {
                        final MapData firstperson_righthand = new MapData();
                        firstperson_righthand.put("rotation", new IntArrayData(new int[]{0, 135, 0}));
                        firstperson_righthand.put("translation", new IntArrayData(new int[]{0, 0, 0}));
                        firstperson_righthand.put("scale", new ListData(new FloatData(0.40F), new FloatData(0.40F), new FloatData(0.40F)));
                        display.put("firstperson_righthand", firstperson_righthand);
                    }
                    out.put("display", display);
                    
                    final MapData textures = new MapData();
                    final StringData front = new StringData(this.front.apply(name));
                    textures.put("particle", front);
                    textures.put("north", front);
                    textures.put("east", new StringData(this.left.apply(name)));
                    textures.put("south", new StringData(this.back.apply(name)));
                    textures.put("west", new StringData(this.right.apply(name)));
                    textures.put("up", new StringData(this.up.apply(name)));
                    textures.put("down", new StringData(this.down.apply(name)));
                    out.put("textures", textures);
                    return out;
                })
                .withBlockPlaceStateMapper((defaultState, mcContext) -> {
                    final Direction[] directions = mcContext.getInternal()
                            .getNearestLookingDirections();
                    for(Direction direction : directions) {
                        if(direction.getAxis() != Direction.Axis.Y) {
                            return defaultState.withProperty("facing", direction.getOpposite()
                                    .getName2());
                        }
                    }
                    return defaultState;
                });
    }
}

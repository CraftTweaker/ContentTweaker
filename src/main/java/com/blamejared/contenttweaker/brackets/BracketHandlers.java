package com.blamejared.contenttweaker.brackets;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.blocks.types.advanced.CoTBlockAdvanced;
import com.blamejared.contenttweaker.items.types.advance.CoTItemAdvanced;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

/**
 * I advise against using the static methods in this class directly as they may be merged into CrT's BEP at any point.
 * If you need to access the Bracket Expression Parser methods dynamically, you can use
 * ```zencode
 * var myName = "misc";
 * <itemgroup:${myName}>
 * ```
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.BracketHandlers")
@Document("mods/contenttweaker/API/BracketHandlers")
public class BracketHandlers {
    
    /**
     * Gets the itemGroup. Will throw an error if the group could not be found
     *
     * @param tokens What you createDataCompound in the BEP call
     * @return The found MCItemGroup
     * @docParam tokens "misc"
     */
    @ZenCodeType.Method
    @BracketResolver("itemgroup")
    public static MCItemGroup getItemGroup(String tokens) {
        return Arrays.stream(ItemGroup.GROUPS).filter(g -> g.getPath().equals(tokens)).map(MCItemGroup::new).findAny().orElseThrow(() -> new IllegalArgumentException("Could not find itemgroup for '<itemgroup:" + tokens + ">'!"));
    }
    
    
    /**
     * Gets a {@link MCToolType}. Will create a new one if the given one does not exist.
     *
     * @param tokens What you would createDataCompound in the BEP call
     * @return The {@link MCToolType} if found, or a new MCToolType
     * @docParam tokens "shovel"
     */
    @ZenCodeType.Method
    @BracketResolver("tooltype")
    public static MCToolType getToolType(String tokens) {
        return new MCToolType(ToolType.get(tokens));
    }
    //
    //    /**
    //     * Gets the {@link MCSoundType}. Will throw an exception if not found.
    //     *
    //     * @param tokens What you would createDataCompound in the BEP call
    //     * @return The found {@link MCSoundType}
    //     * @docParam tokens "ground"
    //     */
    //    @ZenCodeType.Method
    //    @BracketResolver("soundtype")
    //    public static MCSoundType getSoundType(String tokens) {
    //        final MCSoundType fromString = MCSoundType.getFromString(tokens.toUpperCase());
    //        if(fromString != null) {
    //            return fromString;
    //        }
    //        throw new IllegalArgumentException("Could not find soundtype <soundtype:" + tokens + ">!");
    //    }
    //

    /**
     * Gets the registered advanced CoT item. Will throw an error if the item could not be found
     * @param tokens What you would createDataCompound in the BEP call
     * @return The found IIsCotItem
     * @docParam tokens "test_item"
     */
    @ZenCodeType.Method
    @BracketResolver("advanceditem")
    public static CoTItemAdvanced getCoTItem(String tokens) {
        IIsCotItem item = VanillaFactory.REGISTRY.getItem(tokens);
        if (item instanceof CoTItemAdvanced) {
            return ((CoTItemAdvanced) item);
        }
        throw new IllegalArgumentException("Could not get item with name: <advanceditem:" + tokens + ">! Item does not appear to exist!");
    }

    /**
     * Gets the registered advanced CoT block. Will throw an error if the block could not be found
     * @param tokens What you would createDataCompound in the BEP call
     * @return The found IIsCotBlock
     * @docParam tokens "test_block"
     */
    @ZenCodeType.Method
    @BracketResolver("advancedblock")
    public static CoTBlockAdvanced getCoTBlock(String tokens) {
        IIsCoTBlock block = VanillaFactory.REGISTRY.getBlock(tokens);
        if (block instanceof CoTBlockAdvanced) {
            return ((CoTBlockAdvanced) block);
        }
        throw new IllegalArgumentException("Could not get block with name: <advancedblock:" + tokens + ">! Block does not appear to exist!");
    }
}

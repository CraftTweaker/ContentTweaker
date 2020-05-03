package com.blamejared.contenttweaker.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

/**
 * An item Group (a.k.a. Creative Tab) is a grouping of items based on category.
 *
 * @docParam this <itemgroup:misc>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemGroup")
@Document("mods/contenttweaker/item/MCItemGroup")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemGroup", displayStringFormat = "%s.getCommandString()")
public class MCItemGroup implements CommandStringDisplayable {
    
    private final ItemGroup internal;
    
    public MCItemGroup(ItemGroup internal) {
        this.internal = internal;
    }
    
    public ItemGroup getInternal() {
        return internal;
    }
    
    /**
     * Gets the path of the item group.
     * The path is what you use in the Bracket Expression after teh `<itemgroup:` part.
     */
    @ZenCodeType.Getter("path")
    @ZenCodeType.Method
    public String getPath() {
        return internal.getPath();
    }
    
    
    /**
     * Sets the image name of the Background that is used for this tab in the creative menu
     * @param texture The texture to be used
     *
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setBackgroundImageName(String texture) {
        final ItemGroup itemGroup = internal.setBackgroundImageName(texture);
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    
    /**
     * Removes the title of the item Group in the creative inventory
     *
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setNoTitle() {
        final ItemGroup itemGroup = internal.setNoTitle();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    /**
     * Removes the scrollbar of the item Group in the creative inventory
     *
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setNoScrollbar() {
        final ItemGroup itemGroup = internal.setNoScrollbar();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    @Override
    public String toString() {
        return internal.toString();
    }
    
    @Override
    public String getCommandString() {
        return "<itemgroup:" + getPath() + ">";
    }
}

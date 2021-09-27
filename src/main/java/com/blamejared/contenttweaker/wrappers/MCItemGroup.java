package com.blamejared.contenttweaker.wrappers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.item.ItemGroup;
import org.openzen.zencode.java.ZenCodeType;

/**
 * An item Group (a.k.a. Creative Tab) is a grouping of items based on category.
 *
 * @docParam this <itemgroup:misc>
 *
 * @deprecated This class has been replaced by `crafttweaker.api.item.ItemGroup`
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemGroup")
@Document("mods/contenttweaker/API/item/MCItemGroup")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemGroup", displayStringFormat = "%s.getCommandString()")
public class MCItemGroup implements CommandStringDisplayable {
    
    private final ItemGroup internal;

    public MCItemGroup(ItemGroup internal) {
        this.internal = internal;
        // If someone is using this class, tell them to stop
        CraftTweakerAPI.logWarning("MCItemGroup is now part of CraftTweaker, you should only be using `crafttweaker.api.item.ItemGroup` instead!");
    }
    
    public ItemGroup getInternal() {
        return internal;
    }

    /**
     * Gets the path of the item group.
     * The path is what you use in the Bracket Expression after the `<itemGroup:` part.
     */
    @ZenCodeType.Getter("path")
    @ZenCodeType.Method
    public String getPath() {
        return internal.getPath();
    }
    
    
    /**
     * Sets the image name of the Background that is used for this tab in the creative menu
     *
     * @param texture The texture to be used
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
        return "<itemGroup:" + getPath() + ">";
    }

    @ZenCodeType.Caster(implicit = true)
    public ItemGroup asItemGroup(){
        return internal;
    }
}
